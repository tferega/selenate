#!/bin/bash

PROJECT_DIR=`dirname $(readlink -f $0)`

$PROJECT_DIR/../sbt.sh "project SelenateServer" "$@"
