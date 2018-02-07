#!/bin/bash
# file: compile.sh
g++ -ggdb `pkg-config --cflags --libs opencv` -stdlib=libc++ $1 -o $2 -v
