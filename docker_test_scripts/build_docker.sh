#!/bin/bash
if docker-compose up -d --build; then
   exit 1 
fi

