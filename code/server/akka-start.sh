#!/bin/bash

echo Starting up Akka - ELVIS HAS LEFT THE BUILDING ...
`dirname $0`/sbt.sh --loop "$@" '~run-main net.selenate.server.EntryPoint'
