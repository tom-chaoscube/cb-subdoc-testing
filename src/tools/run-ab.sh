#!/bin/bash

OUTPUT_DIR="plot-data"

mkdir -p ./$OUTPUT_DIR

END_POINTS=( fulldoc )
MIN_RESULTS=1
MAX_RESULTS=5


for end_point in "${END_POINTS[@]}"
do
        mkdir -p ./$OUTPUT_DIR/$end_point
        for ((i=$MIN_RESULTS; i<=$MAX_RESULTS; i++)); do
                 ab -n 10000 -c 4 -l -e ./$OUTPUT_DIR/$end_point/plot-data-$i "http://localhost:8080/"$end_point"?keyRoot=youtube-data-posStart-"$i
        done
done

