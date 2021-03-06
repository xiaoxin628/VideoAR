#include <opencv2/opencv.hpp>
#include <opencv2/core.hpp>
#include <opencv2/tracking.hpp>
#include <opencv2/core/ocl.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/videoio.hpp>
#include <opencv2/video/background_segm.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/video.hpp>
#include <ctime>
#include "../samples_utility.hpp"
#include <vector>
//C
#include <stdio.h>
//C++
#include <iostream>
#include <sstream>

#ifdef HAVE_OPENCV
#include <opencv2/flann.hpp>
#endif

#define RESET   "\033[0m"
#define RED     "\033[31m"      /* Red */
#define GREEN   "\033[32m"      /* Green */

using namespace cv;
using namespace std;

// Global variables
//Mat frame; //current frame
Mat fgMaskMOG2; //fg mask fg mask generated by MOG2 method
Ptr<BackgroundSubtractor> pMOG2; //MOG2 Background subtractor
//char keyboard; //

///** Function Headers */
void saveVideo(char* videoFilename);

int main(int argc, char** argv) {
	// show help
	cout << " Usage: example_tracking_multitracker <video_name> [algorithm]\n"
			" examples:\n"
			" example_tracking_multitracker Bolt/img/%04d.jpg\n"
			" example_tracking_multitracker faceocc2.webm MEDIANFLOW\n"
			" \n"
			" Note: after the OpenCV libary is installed,\n"
			" please re-compile with the HAVE_OPENCV parameter activated\n"
			" to enable the high precission of fps computation.\n" << endl;

// timer
#ifdef HAVE_OPENCV
	cvflann::StartStopTimer timer;
#else
	clock_t timer;
#endif

// for showing the speed
	double fps;
	String text;
	char buffer[50];

// set the default tracking algorithm
	String trackingAlg = "KCF";

// create the tracker
	MultiTracker trackers;

// container of the tracked objects
	vector<Rect> ROIs;
	vector<Rect2d> objects;

// set input video
	String video = "../demo.mp4";
	VideoCapture cap(video);

	// Setup output video
	//VID_20180216_124424.mp4
	//demo_out.mp4
	//    cv::VideoWriter output_cap("../demo.mp4",
	cv::VideoWriter output_cap("../demo_me_walking_out.mp4",
			cap.get(CV_CAP_PROP_FOURCC), cap.get(CV_CAP_PROP_FPS),
			cv::Size(cap.get(CV_CAP_PROP_FRAME_WIDTH),
					cap.get(CV_CAP_PROP_FRAME_HEIGHT)));
	   if (!output_cap.isOpened())
	   {
	       std::cout << "!!! Output video could not be opened" << std::endl;
	       return EXIT_FAILURE;
	   }

	   // Read first frame
	Mat frame;

// get bounding box
	cap >> frame;
	selectROIs("tracker", frame, ROIs);

//quit when the tracked object(s) is not provided
	if (ROIs.size() < 1)
		return 0;

	std::vector<Ptr<Tracker> > algorithms;
	for (size_t i = 0; i < ROIs.size(); i++) {
		algorithms.push_back(createTrackerByName(trackingAlg));
		objects.push_back(ROIs[i]);
	}

// initialize the tracker
	trackers.add(algorithms, frame, objects);

// do the tracking
	printf(GREEN "Start the tracking process, press ESC to quit.\n" RESET);
	for (;;) {
		// get frame from the video
		cap >> frame;

		// stop the program if no more images
		if (frame.rows == 0 || frame.cols == 0)
			break;

		// start the timer
#ifdef HAVE_OPENCV
		timer.start();
#else
		timer = clock();
#endif

		//update the tracking result
		trackers.update(frame);

		// calculate the processing speed
#ifdef HAVE_OPENCV
		timer.stop();
		fps=1.0/timer.value;
		timer.reset();
#else
		timer = clock();
		trackers.update(frame);
		timer = clock() - timer;
		fps = (double) CLOCKS_PER_SEC / (double) timer;
#endif

       	//create a single-channel mask the same size as the image filled with 1
       	cv::Mat inverseMask(frame.size(), CV_8UC1, cv::Scalar(1));

		// draw the tracked object
		for (unsigned i = 0; i < trackers.getObjects().size(); i++){

			rectangle(frame, trackers.getObjects()[i], Scalar(255, 0, 0), 2, 1);

	       	//Specify the ROI in the mask
	       	cv::Mat inverseMaskROI = inverseMask(trackers.getObjects()[i]);
	       	//Fill the mask's ROI with 0
	       	inverseMaskROI = cv::Scalar(0);
//	       	//save the frame
//	       	output_cap.write(inverseFill);
		}

       	//Set the image to 0 in places where the mask is 1
       	frame.setTo(cv::Scalar(0,255,0), inverseMask);


		// draw the processing speed
		sprintf(buffer, "speed: %.0f fps", fps);
		text = buffer;
		putText(frame, text, Point(20, 20), FONT_HERSHEY_PLAIN, 1,
				Scalar(255, 255, 255));

		// show image with the tracked object
		imshow("tracker", frame);
		output_cap.write(frame);
		//quit on ESC button
		if (waitKey(1) == 27)
			break;
	}
	output_cap.release();

}

