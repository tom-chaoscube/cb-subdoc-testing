#!/bin/bash

OUTPUT_DIR="plot-data"

mkdir -p ./$OUTPUT_DIR

END_POINTS=( subdoc )
MIN_RESULTS=1
MAX_RESULTS=25


for end_point in "${END_POINTS[@]}"
do
        mkdir -p ./$OUTPUT_DIR/$end_point
        for ((i=$MIN_RESULTS; i<=$MAX_RESULTS; i+=5)); do
                 ab -n 10000 -c 4 -l -e ./$OUTPUT_DIR/$end_point/plot-data-$i "http://localhost:8080/"$end_point"?keyRoot=youtube-data-posStart-"$i
                 if (($i == 1)); then i=0; fi
        done
done

