# JR COURSE

Title: JR Course API  
Author: Alexey Orkhoyan  
Date: 04.11.2020
---
### JR Course API project
Japanese language learning project.

Available functionality
---
1. View word list and grammar list
2. Add or remove word and grammar to personal list
3. Create, edit and remove note
4. Solve grammar exercises
5. The administrator can see the list of users and delete them
6. Administrator can add, edit and remove words and grammar to lists

Interaction types
---
There are two ways to fully interact with this app:
1. Postman API
2. Swagger UI

Using
---
1. Dictionary section, which has three difficulty levels.  
The user can add, change and delete a word. Each word can be written in
Japanese characters, Japanese kana (hiragana and katakana), in Russian,
as well as an additional description with examples of use. There is a word
search with the following logic:
    - The search is performed by hieroglyph, kana or Russian spelling (not
    only for the whole word, but also for its part).
    - In each section (level), the search is performed according to the
    level at which the user is (easy, medium or hard).
    - In the general section of the dictionary, the search is carried out
    regardless of the level.
    - If the search word is not found, an empty list is returned to the
    user (and a button appears to view the entire list).
2. Section of grammar. Works in the same way as the dictionary section. 5 levels,
according to the JLPT exam levels. There is sorting by levels,
search by part of a word.
3. There are two sections in the user's personal account - a personal
dictionary and a personal set of grammar.  
   In the general dictionary, a user has the opportunity
   to add each word to his dictionary, and remove it. The same
   functionality is implemented in the grammar. In personal materials, data
   can be deleted.
4. The grammar contains learning exercises that the user can solve.
5. The user can create, edit and remove notes on his personal account.
6. A user with the ADMIN role has access to a personal account, where he
can view complete information about all users. It is also possible to
delete a user one at a time, or all users except the administrator.


Technologies used in the project
---
- Application architecture and networking - `Spring Boot`
- Working with entities using `Spring Data JPA`.
- Logging is being did using `slf4j` and `Logback`.
- Testing - `JUnit 4, Spring Boot Starter Test, Mockito`
- DBMS - `MySQL`
- Documentation - `Swagger`
- Dependency manager - `Maven`
- Application Server - `WebSphere`
---
Note
---

The connection to the database is done on the `application.properties` file.


