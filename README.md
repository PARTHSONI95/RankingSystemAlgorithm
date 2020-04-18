##Project Title : Program structures & Algorithm - Ranking System##

This project aims at developing the Ranking System algorithm using the dataset where dataset contains the data of cricket matches between teams for past 3 years. Dataset consists of different factors to be accounted for defining ranking system. The probability derived for different factors ultimately helps to compare the performance of the teams and predict the winning chances of any team when match will be played in the future.

**Description of files**

- Dao class file contains code for connecting to MySQL database and fetching data.
- Pojo package contains classes for beans used in application.
- Service package contains classes related to calculating various statistical parameters and probability.

**Prerequisites**

- Any IDE like Eclipse or Intellij required to run the code. Here Eclipse is used as development environment.
- MySQL database is required to load dataset and fetch from it.

**Installation**

- Clone the repository `https://github.com/Harshit2512/RankingSystemAlgorithm.git' in local directory.
- Import project in IDE.
- Refer CSV file in resourse folder and create Database and table by running below commands from MySQL CLI.

mysql> create database psadb;

mysql> use psadb;

CREATE TABLE matcheshistory (
team VARCHAR(40),
opponent VARCHAR(40),
matches INT,
won INT,
lost INT,
average double,
rpo double
);

mysql> show tables;

LOAD DATA LOCAL INFILE "S:/MatchesHistory.csv" INTO TABLE psadb.matcheshistory
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(Team, Opponent, Matches, Won, Lost, average, RPO);

- Note: Make sure to change the directory path of CSV file from correct local directory.

** Running the code**

- Change Database User and password as required by your MySQL set up in DBOperation class.
- Run the program as Java Application from DataService class.

**Versioning**

- GitHub and Git bash are used for version controlling.

**Developers**

Harshit Shukla (NUID - 1080358), Email - shukla.har@husky.neu.edu
Parth Soni (NUID - 1087468), Email - soni.pa@husky.neu.edu