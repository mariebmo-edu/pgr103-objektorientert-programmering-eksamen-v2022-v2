create DATABASE quizDb;

use quizDb;

create table multichoiceQuiz
(
    id            int not null auto_increment primary key,
    question      varchar(255),
    answerA       varchar(255),
    answerB       varchar(255),
    answerC       varchar(255),
    answerD       varchar(255),
    correctAnswer varchar(255)
);

create table binaryQuiz
(
    id            int not null auto_increment primary key,
    question      varchar(255),
    correctAnswer varchar(255)
);

create table score
(
    id    int not null auto_increment primary key,
    user  varchar(100),
    score int,
    topic varchar(100)
);