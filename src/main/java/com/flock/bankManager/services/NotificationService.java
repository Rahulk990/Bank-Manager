package com.flock.bankManager.services;

import com.flock.bankManager.models.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class NotificationService {
    private final int batchSize = 10;
    private final BlockingDeque<Notification> notificationsQueue = new LinkedBlockingDeque<>();
    private Timer countdownTimer = new Timer();

    public NotificationService() {
        resetTimer();
    }

    private void resetTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer Ran Out");
                sendBatch();
                resetTimer();
            }
        };
        countdownTimer.cancel();
        countdownTimer = new Timer();
        countdownTimer.schedule(timerTask, 10000);
    }

    private synchronized void sendBatch() {
        ArrayList<Notification> notifications = new ArrayList<>();
        notificationsQueue.drainTo(notifications, batchSize);

        // Send to Notification Handler
        for (Notification notification : notifications) {
            notification.print();
        }
    }

    public synchronized void submitNotification(Notification notification) {
        try {
            System.out.println("Notification Submitted");
            notificationsQueue.put(notification);
            if (notificationsQueue.size() == batchSize) {
                System.out.println("Batch Complete");
                sendBatch();
                resetTimer();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
