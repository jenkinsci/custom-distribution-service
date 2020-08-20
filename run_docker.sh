#!/bin/bash
if docker-compose up; then
   echo "Command Returned an error"
   exit 1 
