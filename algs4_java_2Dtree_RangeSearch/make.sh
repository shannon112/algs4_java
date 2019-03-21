#!/bin/bash

# Get current position
TOP_DIR=$(pwd)

# Add all necessary jars
LIBPATH=/home/shannon/Documents/java/com_lib/algs4.jar:/home/shannon/Documents/java/com_lib/stdlib.jar

#compile java file
#javac -cp $LIBPATH                  src/com/demo/DemoService.java  -d ./classes/.
#javac -cp $TOP_DIR/classes:$LIBPATH src/com/server/ServerImpl.java -d ./classes/.
#javac -cp $TOP_DIR/classes:$LIBPATH src/com/server/Server.java     -d ./classes/.
#javac -cp $TOP_DIR/classes:$LIBPATH src/com/client/Client.java     -d ./classes/.

javac -cp $LIBPATH: src/RangeSearch.java -d ./bin/.
#javac -cp $LIBPATH: src/KdTree.java -d ./bin/.
#javac -cp $LIBPATH: src/Backup.java -d ./bin/.
