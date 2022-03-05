package com.amigoscode.clients.notification;

public record NotificationRequest(
        Integer customerId,
        String message,
        String email
) {
}
