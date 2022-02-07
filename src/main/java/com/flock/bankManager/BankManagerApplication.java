package com.flock.bankManager;

import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import com.flock.bankManager.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class BankManagerApplication implements CommandLineRunner {

    @Autowired
    private BankService bankService;

    public static void main(String[] args) {
        SpringApplication.run(BankManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 1000; i++) {
            bankService.updateName(new UpdateReq("r@gmail.com", "abc"));
            bankService.withdrawAmount(new TransactionReq("r@gmail.com", 100));
            bankService.depositAmount(new TransactionReq("r@gmail.com", 200));
        }
    }
}


