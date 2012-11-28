@echo off

echo Starting up Akka - ELVIS HAS LEFT THE BUILDING ...
call "%~dp0sbt.bat" --loop %* "~run-main net.selenate.server.EntryPoint"
