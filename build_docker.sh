#!/bin/bash
if docker-compose up -d --build; then
   echo "Command Returned an error"
   exit 1 
