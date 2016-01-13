#!/bin/bash

function clean_up {
	echo "Cleaning /tmp ..."
	rm /tmp/.X*-lock
	rm /tmp/temp*.sh
	rm -rf /tmp/*webdriver-profile	
}

clean_up && /opt/selenate/bin/start
