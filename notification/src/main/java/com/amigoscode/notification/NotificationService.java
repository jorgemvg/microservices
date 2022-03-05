package com.amigoscode.notification;

import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest notificationRequest) {
        notificationRepository.save(
                new Notification().builder()
                        .toCustomerId(notificationRequest.customerId())
                        .toCustomerEmail(notificationRequest.email())
                        .message(notificationRequest.message())
                        .sender("amigoscode")
                        .sendAt(LocalDateTime.now())
                        .build());
    }

}