# AccertifyCodingChallenge
This application is a REST api that saves, fetches, and deletes words and their subwords

Installation
1. Create postgresql instance and table with script
```sql
create table subwords
(
    word varchar(255) not null
        primary key,
    sub_words text[]
);
```
2. Add VM option -Ddatabase-username=<user> from table creation

How to use

/findSubWords 
/addWord
/deleteWord
/getWordAndSubwords
All have a request body of 
```
{
"word": "ladybug"
}

/listAllWords has no request body or params.