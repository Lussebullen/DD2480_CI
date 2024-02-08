#!/bin/sh

hash=$1                                    
path_logs="../../src/main/resources/logs/" 
logfile="$path_logs/$hash.log"
original_directory=$PWD

## Consider providing as argument?
git clone -b assessment https://github.com/Lussebullen/DD2480_CI.git cirepo
cd cirepo/decide

## Create log directories if non-existing
mkdir -p $path_logs 

## Write date, time, commit id and compile results to file
printf "Date: " > $logfile
date +%Y/%m/%d >> $logfile
printf "Time: " >> $logfile
date +%H:%M:%S >> $logfile
printf "Commit ID: $hash\n" >> $logfile
printf "Compilation Logs\n" >> $logfile
mvn compile >> $logfile


if grep -q FAILURE $logfile 
then
    exit 1
fi


## Write date, time, commit id and test results to file
printf "Test Logs\n" >> $logfile
mvn test >> $logfile

if grep -q FAILURE $logfile 
then
    exit 1
fi

 ## HTML format by replacing newlines with break tags
 ## Kudos to https://stackoverflow.com/questions/1251999/how-can-i-replace-each-newline-n-with-a-space-using-sed
 sed -i -e ':a' -e 'N' -e '$!ba' -e 's/\n/<br>\n/g' $logfile

cd $original_directory
rm -rf cirepo

