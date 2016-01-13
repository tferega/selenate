#!/bin/bash

rm -rf pack/*

# Remove previous compiled artifacts
pushd ../../code
rm -rf Server/target/pack

# Compile and pack the code, and copy it to target folder
pack.sh
popd

cp ../../code/Server/target/pack/* pack/ -r