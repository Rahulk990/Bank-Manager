package com.flock.bankManager.utils;

public interface DBConstants {

    interface Variables {
        String SQL_EMAIL = "email";
        String SQL_NAME = "name";
        String SQL_BALANCE = "balance";
        String SQL_AMOUNT = "amount";
    }

    interface Queries {
        String SQL_INSERT_USER = "insert into users_table (email, name) values (:email, :name)";
        String SQL_UPDATE_NAME = "update users_table set name = :name where email = :email";
        String SQL_DEPOSIT_AMT = "update users_table set balance = balance + :amount where email = :email";
        String SQL_WITHDRAW_AMT = "update users_table set balance = balance - :amount where email = :email and balance >= :amount";
    }
}
