@echo off

echo Publishing project...
call "%~dp0sbt.bat" --no-jrebel %* clean publish
