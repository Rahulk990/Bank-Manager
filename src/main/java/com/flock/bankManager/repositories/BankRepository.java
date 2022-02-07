package com.flock.bankManager.repositories;

import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.flock.bankManager.utils.DBConstants.Queries.*;
import static com.flock.bankManager.utils.DBConstants.Variables.*;

@Repository
public class BankRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public String updateName(UpdateReq updateReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, updateReq.getEmail());
        params.addValue(SQL_NAME, updateReq.getName());
        jdbcTemplate.update(SQL_UPDATE_NAME, params);
        return updateReq.getEmail();
    }

    public String deposit(TransactionReq transactionReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, transactionReq.getEmail());
        params.addValue(SQL_AMOUNT, transactionReq.getAmount());
        jdbcTemplate.update(SQL_DEPOSIT_AMT, params);
        return transactionReq.getEmail();
    }

    public String withdraw(TransactionReq transactionReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, transactionReq.getEmail());
        params.addValue(SQL_AMOUNT, transactionReq.getAmount());
        jdbcTemplate.update(SQL_WITHDRAW_AMT, params);
        return transactionReq.getEmail();
    }

    public void insertUser(UpdateReq updateReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, updateReq.getEmail());
        params.addValue(SQL_NAME, updateReq.getName());
        jdbcTemplate.update(SQL_INSERT_USER, params);
    }
}
