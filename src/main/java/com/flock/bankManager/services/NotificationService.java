package com.flock.bankManager.services;

import com.flock.bankManager.models.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

@Service
public class NotificationService {
    private final int batchSize = 10;
    private final BlockingDeque<Notification> notificationsQueue = new LinkedBlockingDeque<>();
    private final BlockingDeque<Notification> retryQueue = new LinkedBlockingDeque<>();
    private final ExecutorService sendNotificationService = Executors.newSingleThreadExecutor();
    private final ScheduledExecutorService retryService = Executors.newSingleThreadScheduledExecutor();
    private Timer countdownTimer = new Timer();

    public NotificationService() {
        resetTimer();
        retryService.scheduleWithFixedDelay(this::retryNotifications, 10, 10, TimeUnit.SECONDS);
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

    private void handleNotification(Notification notification) {
        if (Math.random() > 0.50) {
            System.out.println("Notification Processed");
        } else {
            try {
                System.out.println("Notification Failed");
                retryQueue.put(notification);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void retryNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        retryQueue.drainTo(notifications, batchSize);

        for (Notification notification : notifications) {
            System.out.println("Retry: " + notification.stringify());
            sendNotificationService.submit(() -> handleNotification(notification));
        }
    }

    private synchronized void sendBatch() {
        ArrayList<Notification> notifications = new ArrayList<>();
        notificationsQueue.drainTo(notifications, batchSize);

        for (Notification notification : notifications) {
            System.out.println("First Try: " + notification.stringify());
            sendNotificationService.submit(() -> handleNotification(notification));
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
