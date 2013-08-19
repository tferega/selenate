#!/bin/bash

echo 'Starting logging ...'
DATE=$(date +%Y%m%d)
`dirname $0`/akka-start.sh --loop --no-jrebel --no-pause 22>&1 | tee -a `dirname $0`/../../logs/selenate-"$DATE".log
