#!/bin/bash

# Quick script to gather and write out sample data for our sub-doc testing
# Note: Create directories and files locally to where it is run, pulls data directly from web etc.

# Google api key credentials must be set as an environment variable API_KEY
OUTPUT_DIR="input-data"

mkdir -p ./$OUTPUT_DIR

MIN_RESULTS=1
MAX_RESULTS=50


for ((i=$MIN_RESULTS; i<=$MAX_RESULTS; i++)); do
	curl -o ./$OUTPUT_DIR/youtube-data-$i 'https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults='$i'&q=beatles&key='$API_KEY
	sed "2i  \"searchTerm\": \"beatles\"," $OUTPUT_DIR/youtube-data-$i > $OUTPUT_DIR/youtube-data-posStart-$i
	sed "$ d" ./$OUTPUT_DIR/youtube-data-$i | sed "$ s/$/,/" | sed "$ a \"searchTerm\": \"beatles\"" | sed "$ a }" > $OUTPUT_DIR/youtube-data-posEnd-$i
	sleep 1
done




