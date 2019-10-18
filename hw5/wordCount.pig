copyFromLocal input.txt class5/input.txt; --input.txt contains data to be searched
copyFromLocal terms.txt class5/terms.txt; --terms.txt contains the strings to search for
lines = LOAD 'class5/input.txt' AS (line:chararray);
lines1 = FOREACH lines GENERATE REPLACE($0, '#', ' ');
lines2 = FOREACH lines1 GENERATE REPLACE($0, ';', ' ');
lines3 = FOREACH lines2 GENERATE REPLACE($0, '-', ' ');
lines4 = FOREACH lines3 GENERATE REPLACE($0, ':', ' ');
lines5 = FOREACH lines4 GENERATE REPLACE($0, '/', ' ');
tokens = FOREACH lines5 GENERATE TOKENIZE(LOWER($0)) as token;
tokens1 = RANK tokens;
tokenByLine = FOREACH tokens1 GENERATE $0 as lineID, FLATTEN($1);

keys = LOAD 'class5/terms.txt' AS (key:chararray);
data = CROSS tokenByLine, keys;
filter_result = FILTER data BY LOWER($2) == $1;
filter_result1 = DISTINCT filter_result;
grouped = GROUP filter_result1 BY $2;
summary = FOREACH grouped GENERATE group, COUNT($1); --do not have keys with 0 appearance
joined = JOIN keys BY key LEFT OUTER, summary BY $0;
final = FOREACH joined GENERATE $0, ($1 is null ? 0 : $2);

rmf class5/output;
STORE (ORDER final BY $0) INTO  'class5/output';
sh rm -rf output;
copyToLocal class5/output output;
sh cat output/* > output.txt;