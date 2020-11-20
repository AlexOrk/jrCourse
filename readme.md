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
3. Create, edit and remove notes
4. Solve grammar exercises
5. The administrator can see the list of users and delete them
6. The administrator can add, edit and remove words and grammar to lists

Interaction types
---
There are two ways to fully interact with this app:
1. Postman API
2. Swagger UI

Using
---
1. Dictionary section, which has three levels of difficulty.  
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

Working with RabbitMQ
---
The application has built-in functionality for working with queues using the
producer-consumer pattern, implemented using RabbitMQ.  
Each time the user starts searching for a collection of objects ("/words",
"/grammar", "/exercises", "/users" or "/notes"), the collection is passed to
the producer. Depending on the type of object (Word, Grammar, Exercise, User
or Note, the DirectExchange ("x.get.work") directs the collection to the
appropriate queue:
- "q.get.word.work"
- "q.get.grammar.work"
- "q.get.exercise.work"
- "q.get.user.work"
- "q.get.note.work"  

Then the consumer receives the collection and, using logging, outputs the data
to the console and writes to a file. If the collection is empty, an
AmqpRejectAndDontRequeueException is thrown. The Direct Exchange ("x.get.dead")
then tries to resubmit the object to the consumer. This happens once every 3
seconds, the time for each next attempt is doubled (but the interval is not more
than 10 seconds and only 5 attempts). If this fails, then the message is sent to
the appropriate queue ("q.get.*.dead").  

The application also has a dedicated controller for working with queues
(MqController).  
It can be used to save objects (Word, Grammar, Exercise or Note) to the database.
To store the object, the Direct Exchange ("x.post.work") routes the object to the
appropriate queue ("q.post.\*.work"). Then the consumer receives the object and
writes the data to the database. In case the consumer finds an error, the Direct
Exchange ("x.post.dead") tries to resend the message. This also happens once every
3 seconds, the time for each next attempt is doubled (but the interval is not more
than 10 seconds and only 5 attempts). If this fails, then the message is sent to
the appropriate queue ("q.post.*.dead"). The user receives a message about the
passage and processing of the request, but not about the result. In case of failure,
the consumer using logging will display an error message in the console and write it
to a file.

To demonstrate how it works in case of an exception, the controller "/mq/saveWord"
does not have a validation check in the form of an @Valid annotation and an
appropriate filter method ("isCorrectWord") is added on the consumer's side.


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
- Message-oriented middleware - `RabbitMQ`
---
Note
---

Connection to the database, to RabbitMQ, is done using the `application.properties` file.

For testing, you can use next templates:
- Word ("description": "", "jpKana": "ぎんこう", "jpKanji": "銀行", "level": "easy", "ruWord": "банк")
- Грамматика ("level": 2, "formula": "〜の際", "example": "退院の際には色々とお世話になりまして有難うございました",
"description": "Временной союз со значением «во время», «при», после глагола - дополнительное значение
«в случае». «Большое вам спасибо за вашу заботу в момент моей выписки из больницы».")