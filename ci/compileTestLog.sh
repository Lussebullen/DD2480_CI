#!/bin/sh

hash=$1                                    
path_logs="../../src/main/resources/logs/" 
path_compile_logs="$path_logs/compilelogs" 
path_test_logs="$path_logs/testlogs"       


## Spawn subshell
(
git clone -b assessment https://github.com/Lussebullen/DD2480_CI.git cirepo
## Consider providing as argument?
cd cirepo/decide

## Create log directories if non-existing
mkdir -p $path_logs 
#mkdir -p $path_compile_logs
#mkdir -p $path_test_logs

## Write date, time, commit id and compile results to file
printf "Date: " > "$path_logs/$hash.log"
date +%Y/%m/%d >> "$path_logs/$hash.log"
printf "Time: " >> "$path_logs/$hash.log"
date +%H:%M:%S >> "$path_logs/$hash.log"
printf "Commit ID: $hash\n" >> "$path_logs/$hash.log"
printf "Compilation Logs\n" >> "$path_logs/$hash.log"
mvn compile >> "$path_logs/$hash.log"


if grep -q failure "$path_logs/$hash.log" 
then
    exit 1
fi


## Write date, time, commit id and test results to file
printf "Test Logs\n" >> "$path_logs/$hash.log"
mvn test >> "$path_logs/$hash.log"

if grep -q failure "$path_logs/$hash.log" 
then
    exit 1
fi

 ## HTML format by replacing newlines with break tags
 ## Kudos to https://stackoverflow.com/questions/1251999/how-can-i-replace-each-newline-n-with-a-space-using-sed
 sed -i -e ':a' -e 'N' -e '$!ba' -e 's/\n/<br>\n/g' "$path_logs/$hash.log"

)

rm -rf cirepo

