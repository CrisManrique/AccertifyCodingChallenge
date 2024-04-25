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
```
/listAllWords has an optional request body where you can
type the size or limit the amount of words you receive in the response
.
For example
```java
{
    "recordFrom": 1,
    "recordTo": 5
}
```

All endpoints are accessed through postman http://localhost:8080/<endpoint>