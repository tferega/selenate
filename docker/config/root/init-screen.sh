#!/bin/bash

if [[ "$#" -ne 1 ]]; then
  echo 'Usage: init-screen SCREENNUM'
  exit 0
fi

scr="$1"
ip=$(ip addr | grep global | tail -n1 | awk '{print $2}' | cut -f1 -d'/')

echo 'Initializing screen '"$scr"' for ip '"$ip"
DISPLAY=:"$scr"

echo 'Starting Xvfb'
Xvfb :"$scr" -screen 0 1024x768x16 -ac &
sleep 1

echo 'Starting icewm'
icewm --display=:"$scr" &
sleep 1

echo 'Starting x11vnc'
x11vnc -display :"$scr" -listen "$ip" -nopw -xkb -shared -forever &
