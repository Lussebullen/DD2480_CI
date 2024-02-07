#!/bin/sh

hash=$1                              ## Provided as command line argument
path_logs="logs"                     ## Change to correct path
path_compile_logs="logs/compilelogs" ## Change to correct path
path_test_logs="logs/testlogs"       ## Change to correct path

## Create directories if non-existing
mkdir -p $path_logs 
mkdir -p $path_compile_logs
mkdir -p $path_test_logs

## Spawn subshell
(
## Consider providing as argument?
#cd latestRepoClone/DD2480-CI/ci
#mvn clean

## Compile branch and dump result and hash in log file with json format
## Change format after deciding on it
printf "{\n\t \"id\":$hash,\n\t \"log\": \"" > "$path_compile_logs/$hash.log"
mvn compile >> "$path_compile_logs/$hash.log"
printf "\"\n}" >> "$path_compile_logs/$hash.log"

if grep -q failure "$path_compile_logs/$hash.log" 
then
    echo "compile failure!" && exit 1
fi


## Test branch and dump result and hash in log file with json format
## Change format after deciding on it
printf "{\n\t \"id\":$hash,\n\t \"log\": \"" > "logs/testlogs/$hash.log"
mvn test >> "logs/testlogs/$hash.log"
printf "\"\n}" >> "logs/testlogs/$hash.log"

if grep -q failure "logs/testlogs/$hash.log" 
then
    echo "Failing tests exists!" && exit 1
else
    echo "Compiled and tested successfully!"
fi

)

##Possible (ugly) workaround to cloning problem
#rm -rf latestRepoClone 

