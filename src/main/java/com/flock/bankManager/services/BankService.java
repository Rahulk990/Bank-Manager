package com.flock.bankManager.services;

import com.flock.bankManager.models.Notification;
import com.flock.bankManager.models.Task;
import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import com.flock.bankManager.repositories.BankRepository;
import com.flock.bankManager.utils.PriorityBasedExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BankService {
    //    private final ExecutorService SQLThreadPool = Executors.newFixedThreadPool(4);
    private final PriorityBasedExecutorService SQLThreadPool = new PriorityBasedExecutorService(4, 50);
    private final ExecutorService notificationThreadPool = Executors.newSingleThreadExecutor();

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private NotificationService notificationService;

    public void insertUser(UpdateReq updateReq) {
        bankRepository.insertUser(updateReq);
    }

    public void updateName(UpdateReq updateReq) {

        SQLThreadPool.scheduleJob(new Task(() -> {
            String email = bankRepository.updateName(updateReq);
            notificationThreadPool.submit(() -> notificationService
                    .submitNotification(new Notification(email, "Name Updated")));
        }, 0));

//        CompletableFuture<Void> completableFuture = CompletableFuture
//                .supplyAsync(() -> bankRepository.updateName(updateReq), SQLThreadPool)
//                .thenAcceptAsync(email -> notificationService
//                        .submitNotification(new Notification(email, "Name Updated")), notificationThreadPool);
    }

    public void depositAmount(TransactionReq transactionReq) {

        SQLThreadPool.scheduleJob(new Task(() -> {
            String email = bankRepository.deposit(transactionReq);
            notificationThreadPool.submit(() -> notificationService
                    .submitNotification(new Notification(email, "Amount Deposited")));
        }, 1));


//        CompletableFuture<Void> completableFuture = CompletableFuture
//                .supplyAsync(() -> bankRepository.deposit(transactionReq), SQLThreadPool)
//                .thenAcceptAsync(email -> notificationService
//                        .submitNotification(new Notification(email, "Amount Deposited")), notificationThreadPool);
    }

    public void withdrawAmount(TransactionReq transactionReq) {

        SQLThreadPool.scheduleJob(new Task(() -> {
            String email = bankRepository.withdraw(transactionReq);
            notificationThreadPool.submit(() -> notificationService
                    .submitNotification(new Notification(email, "Amount Withdrawn")));
        }, 1));

//        CompletableFuture<Void> completableFuture = CompletableFuture
//                .supplyAsync(() -> bankRepository.withdraw(transactionReq), SQLThreadPool)
//                .thenAcceptAsync(email -> notificationService
//                        .submitNotification(new Notification(email, "Amount Withdrawn")), notificationThreadPool);
    }
}
