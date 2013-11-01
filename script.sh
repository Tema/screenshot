#!/bin/bash
for file in $(find . -type f -name \*.java);
do
    cp copyright.txt  tempfile
    cat "$file" >> tempfile;
    mv tempfile "$file";
done
