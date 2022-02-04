# Bank-Manager

## Configuring Database Schema
```
create database bank;
use bank;

create table users_table (
    email varchar(50) primary key,
    name varchar(50) not null,
    balance int not null default 0
);
```
