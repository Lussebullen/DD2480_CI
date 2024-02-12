#!/bin/sh

commit_id=$1
clone_url=$2
branch_name=$3

path_logs="src/main/resources/logs/"
## Create log directories if non-existing
mkdir -p $path_logs
logfile="$path_logs/$commit_id.log"

## Write date, time, commit id to file
printf "Date: "                  > $logfile
date   +%Y/%m/%d                 >> $logfile
printf "Time: "                  >> $logfile
date   +%H:%M:%S                 >> $logfile
printf "SHA: $commit_id"         >> $logfile

local_clone_name=cirepo
rm -rf $local_clone_name
git clone --quiet -b $branch_name $clone_url $local_clone_name
cloneExitCode=`echo $?`
if [ $cloneExitCode -eq 0 ]; then
    printf "Successfully cloned repo from URL: $clone_url. \n \
                        Switched to branch \"$branch_name.\"\n" >> $logfile
else
    printf "Failed to clone repo from URL: $clone_url.\n" >> $logfile
    printf "Exit code: $cloneExitCode\n" >> $logfile
    printf "SHA: $1\n" >> $logfile
    printf "Clone URL: $2\n" >> $logfile
    printf "Branch name: $3\n" >> $logfile
    exit 1;
fi

original_directory=$PWD
cd $local_clone_name/decide
relative_path_logfile="../../$logfile"


printf "Commit ID: $commit_id\n" >> $relative_path_logfile

## Compile project and write log to file
printf "\nCompilation Logs\n"    >> $relative_path_logfile
mvn    compile                   >> $relative_path_logfile


if grep -q FAILURE $relative_path_logfile 
then
    sed -i -e ':a' -e 'N' -e '$!ba' -e 's/\n/<br>\n/g' $relative_path_logfile
    cd $original_directory
    rm -rf $local_clone_name
    exit 1
fi


## Write date, time, commit id and test results to file
printf "\nTest Logs\n" >> $relative_path_logfile
mvn    test            >> $relative_path_logfile

if grep -q FAILURE $relative_path_logfile 
then
    sed -i -e ':a' -e 'N' -e '$!ba' -e 's/\n/<br>\n/g' $relative_path_logfile
    cd $original_directory
    rm -rf $local_clone_name
    exit 1
fi

## HTML format by replacing newlines with break tags
## Kudos to https://stackoverflow.com/questions/1251999/how-can-i-replace-each-newline-n-with-a-space-using-sed
sed -i -e ':a' -e 'N' -e '$!ba' -e 's/\n/<br>\n/g' $relative_path_logfile

cd $original_directory
rm -rf $local_clone_name

