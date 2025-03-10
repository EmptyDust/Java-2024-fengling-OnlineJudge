### Database document
> 2024-07-02 14:27:28
#### answer  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|id| |INT(11)|Y|
2| |paperID| |INT(11)|N|
3| |problemID| |INT(11)|N|
4| |studentID| |INT(11)|N|
5| |content| |VARCHAR(100)|N|
6| |stdAnswer| |VARCHAR(100)|N|
#### papers  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|id| |INT(11)|Y|
2| |title| |VARCHAR(25)|N|
3| |content| |VARCHAR(500)|N|
4| |author| |VARCHAR(25)|N|
5| |date| |DATE|N|
6| |time| |TIME|N|
7| |problemList| |VARCHAR(1000)|N|
8| |scoreList| |VARCHAR(1000)|N|
9| |duration| |INT(11)|N|
10| |status| |VARCHAR(30)|N|
#### problems  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|id| |INT(11)|Y|
2| |title| |VARCHAR(25)|N|
3| |status| |VARCHAR(25)|N|
4| |content| |VARCHAR(25)|N|
5| |optionA| |VARCHAR(100)|N|
6| |optionB| |VARCHAR(100)|N|
7| |optionC| |VARCHAR(100)|N|
8| |optionD| |VARCHAR(100)|N|
9| |optionE| |VARCHAR(100)|N|
10| |optionF| |VARCHAR(100)|N|
11| |optionG| |VARCHAR(100)|N|
12| |optionH| |VARCHAR(100)|N|
13| |type| |VARCHAR(100)|N|
14| |answer| |VARCHAR(1000)|N|
#### students  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|id| |INT(11)|Y|
2| |name| |VARCHAR(25)|N|
3| |password| |VARCHAR(30)|N|
4| |major| |VARCHAR(20)|N|
5| |enrollmentYear| |VARCHAR(20)|N|
6| |class| |VARCHAR(20)|N|
7| |papersID| |VARCHAR(1000)|N|
#### task  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|id| |INT(11)|Y|
2| |paperID| |INT(11)|N|
3| |studentID| |INT(11)|N|
4| |grade| |INT(30)|N|
5| |problemAnswer| |VARCHAR(1000)|N|
#### teachers  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|id| |INT(11)|Y|
2| |name| |VARCHAR(25)|Y|
3| |password| |VARCHAR(30)|N|
4| |major| |VARCHAR(30)|N|
