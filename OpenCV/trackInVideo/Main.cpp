#include <opencv2/opencv.hpp>
#include <opencv2/tracking.hpp>
#include <opencv2/core/ocl.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/videoio.hpp>
#include <opencv2/video/background_segm.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/video.hpp>
#include <vector>
//C
#include <stdio.h>
//C++
#include <iostream>
#include <sstream>


using namespace cv;
using namespace std;

// Global variables
//Mat frame; //current frame
Mat fgMaskMOG2; //fg mask fg mask generated by MOG2 method
Ptr<BackgroundSubtractor> pMOG2; //MOG2 Background subtractor
//char keyboard; //

/** Function Headers */
void saveVideo(char* videoFilename);
Mat getPaddedROI(const Mat &input, int top_left_x, int top_left_y, int width, int height, Scalar paddingColor);

// Convert to string
#define SSTR( x ) static_cast< std::ostringstream & >( \
( std::ostringstream() << std::dec << x ) ).str()

int main(int argc, char **argv)
{
    // List of tracker types in OpenCV 3.2
    // NOTE : GOTURN implementation is buggy and does not work.
    string trackerTypes[6] = {"BOOSTING", "MIL", "KCF", "TLD","MEDIANFLOW", "GOTURN"};
    // vector <string> trackerTypes(types, std::end(types));

    // Create a tracker
    string trackerType = trackerTypes[0];

    Ptr<Tracker> tracker;

    //green background

    // Create empy input img, foreground and background image and foreground mask.
    Mat img, foregroundMask, backgroundImage, foregroundImg;
    // Init background substractor
    Ptr<BackgroundSubtractor> bg_model = createBackgroundSubtractorMOG2().dynamicCast<BackgroundSubtractor>();

    // capture video from source 0, which is web camera, If you want capture video from file just replace //by  VideoCapture cap("videoFile.mov")
//    VideoCapture cap(videoFilename);
//    VideoCapture cap(0);


    #if (CV_MINOR_VERSION < 3)
    {
        tracker = Tracker::create(trackerType);
    }
    #else
    {
        if (trackerType == "BOOSTING")
            tracker = TrackerBoosting::create();
        if (trackerType == "MIL")
            tracker = TrackerMIL::create();
        if (trackerType == "KCF")
            tracker = TrackerKCF::create();
        if (trackerType == "TLD")
            tracker = TrackerTLD::create();
        if (trackerType == "MEDIANFLOW")
            tracker = TrackerMedianFlow::create();
        if (trackerType == "GOTURN")
            tracker = TrackerGOTURN::create();
    }
    #endif
    // Read video
    VideoCapture video("../demo.mp4");

    // Exit if video is not opened
    if(!video.isOpened())
    {
        cout << "Could not read video file" << endl;
        return 1;

    }

    // Setup output video
    cv::VideoWriter output_cap("../demo_out.mp4",
    		video.get(CV_CAP_PROP_FOURCC),
			video.get(CV_CAP_PROP_FPS),
                               cv::Size(video.get(CV_CAP_PROP_FRAME_WIDTH),
                            		   video.get(CV_CAP_PROP_FRAME_HEIGHT)));

    if (!output_cap.isOpened())
    {
        std::cout << "!!! Output video could not be opened" << std::endl;
        return EXIT_FAILURE;
    }



    // Read first frame
    Mat frame;

    bool ok = video.read(frame);

    // Define initial boundibg box
    Rect2d bbox(287, 23, 86, 320);

    // Uncomment the line below to select a different bounding box
    bbox = selectROI(frame, false);

    // Display bounding box.
    rectangle(frame, bbox, Scalar( 255, 0, 0 ), 2, 1 );

    imshow("Tracking", frame);

    tracker->init(frame, bbox);

    while(video.read(frame))
    {
        // Start timer
        double timer = (double)getTickCount();

        // Update the tracking result
        bool ok = tracker->update(frame, bbox);

        // Calculate Frames per second (FPS)
        float fps = getTickFrequency() / ((double)getTickCount() - timer);

        if (ok)
        {

            // Tracking success : Draw the tracked object
            rectangle(frame, bbox, Scalar( 255, 0, 0 ), 2, 1 );
        }
        else
        {
            // Tracking failure detected.
            putText(frame, "Tracking failure detected", Point(100,80), FONT_HERSHEY_SIMPLEX, 0.75, Scalar(0,0,255),2);
        }



        // Display tracker type on frame
        putText(frame, trackerType + " Tracker", Point(100,20), FONT_HERSHEY_SIMPLEX, 0.75, Scalar(50,170,50),2);

        // Display FPS on frame
        putText(frame, "FPS : " + SSTR(int(fps)), Point(100,50), FONT_HERSHEY_SIMPLEX, 0.75, Scalar(50,170,50), 2);

        // Display frame.
//        imshow("Tracking", frame);

        // Exit if ESC pressed.
        int k = waitKey(1);
        if(k == 27)
        {
            break;
        }

        	//test inverse

        	cv::Mat inverseFill(frame.clone());
        	//create a single-channel mask the same size as the image filled with 1
        	cv::Mat inverseMask(inverseFill.size(), CV_8UC1, cv::Scalar(1));

        	//Specify the ROI in the mask
        	cv::Mat inverseMaskROI = inverseMask(bbox);
        	//Fill the mask's ROI with 0
        	inverseMaskROI = cv::Scalar(0);
        	//Set the image to 0 in places where the mask is 1
        	inverseFill.setTo(cv::Scalar(0,255,0), inverseMask);

        	//save the frame
        	output_cap.write(inverseFill);
        imshow("Tracking", inverseFill);
    }
    output_cap.release();
}

void saveVideo(char* videoFilename){

    VideoCapture vcap(videoFilename);
    if(!vcap.isOpened()){
        cout << "Error opening video stream or file" << endl;
    }

    int frame_width=   vcap.get(CV_CAP_PROP_FRAME_WIDTH);
    int frame_height=   vcap.get(CV_CAP_PROP_FRAME_HEIGHT);
    VideoWriter video("out.avi",CV_FOURCC('M','J','P','G'),10, Size(frame_width,frame_height),true);

    for(;;){

        Mat frame;
        vcap >> frame;
        video.write(frame);
        imshow( "Frame", frame );
        char c = (char)waitKey(33);
        if( c == 27 ) break;
    }

}


Mat getPaddedROI(const Mat &input, int top_left_x, int top_left_y, int width, int height, Scalar paddingColor) {
    int bottom_right_x = top_left_x + width;
    int bottom_right_y = top_left_y + height;

    cout << "bottom_right_x:"<<bottom_right_x << endl;
    cout << "bottom_right_y:"<<bottom_right_y << endl;

    Mat output;
    if (top_left_x < 0 || top_left_y < 0 || bottom_right_x > input.cols || bottom_right_y > input.rows) {
        // border padding will be required
        int border_left = 0, border_right = 0, border_top = 0, border_bottom = 0;

        if (top_left_x < 0) {
            width = width + top_left_x;
            border_left = -1 * top_left_x;
            top_left_x = 0;
        }
        if (top_left_y < 0) {
            height = height + top_left_y;
            border_top = -1 * top_left_y;
            top_left_y = 0;
        }
        if (bottom_right_x > input.cols) {
            width = width - (bottom_right_x - input.cols);
            border_right = bottom_right_x - input.cols;
        }
        if (bottom_right_y > input.rows) {
            height = height - (bottom_right_y - input.rows);
            border_bottom = bottom_right_y - input.rows;
        }

        Rect R(top_left_x, top_left_y, width, height);
        copyMakeBorder(input(R), output, border_top, border_bottom, border_left, border_right, 2, paddingColor);
    }
    else {
        // no border padding required
        Rect R(top_left_x, top_left_y, width, height);
        output = input(R);
    }
    return output;
}
