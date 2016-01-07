#!/bin/bash

# Remove previous compiled artifacts
rm -rf ../code/Server/target/pack
rm -rf payload/pack

# Remove old Selenate image

# Compile and pack the code, and copy it to target folder
cd ../code
sbt clean pack
cd ../docker
cp -R ../code/Server/target/pack payload/root

# Build new image
docker build -t selenate .
