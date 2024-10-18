create database if not exists projetoPoo;
use projetoPoo;
create table if not exists marca (
id integer primary key auto_increment,
nome varchar(100) not null,
logo mediumblob
);