#!/bin/sh
for filename in sml/*.html; do
    newfile="$(echo "${filename#"./sml/"}" | cut -f 1 -d '?').xml"
    mv $filename $newfile
done
