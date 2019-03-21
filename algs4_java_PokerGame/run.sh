#!/bin/bash

#Get client/server
SIDE=$1

# Get current position
TOP_DIR=$(pwd)

# Add all necessary jars
LIBPATH=/home/shannon/Documents/java/com_lib/algs4.jar:/home/shannon/Documents/java/com_lib/stdlib.jar

#run program
#
#if [ $SIDE == "server" ];then
#    java -cp $TOP_DIR/classes:$LIBPATH  com/server/Server
#else
#    java -cp $TOP_DIR/classes:$LIBPATH  com/client/Client
#fi

java -cp $TOP_DIR/bin:$LIBPATH Game sample.in
#java -cp $TOP_DIR/bin:$LIBPATH Card sample.in
#java -cp $TOP_DIR/bin:$LIBPATH Player sample.in
