#!/bin/bash

echo Publishing project...
`dirname $0`/sbt.sh --no-jrebel "$@" clean publish
