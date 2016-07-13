#!/bin/bash

## Bump version in code/project/Settings.scala
sbt clean compile +publishSigned
