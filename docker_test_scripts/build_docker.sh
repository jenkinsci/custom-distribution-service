#!/bin/bash
if docker-compose up -d --build; then
   echo "Container built successfully"
else
   echo "Container errored out"
   exit 1
fi

