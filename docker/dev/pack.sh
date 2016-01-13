#!/bin/bash

# Remove previous compiled artifacts
pushd ../../code
rm -rf Server/target/pack

# Compile and pack the code, and copy it to target folder
pack.sh
popd
