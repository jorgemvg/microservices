package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckReponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email valid
        //todo: check if email is not taken

        customerRepository.saveAndFlush(customer);

        //todo: check if fraudster

        /* Como implementamos Open Feing, usamos la interfaz que creamos de openFeing de Fraud
        FraudCheckReponse fraudCheckReponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckReponse.class,
                customer.getId());
        */
        FraudCheckReponse fraudCheckReponse = fraudClient.isFrauster(customer.getId());

        if ( fraudCheckReponse.isFrauster() ) {
            throw new IllegalStateException("Fraudster");
        }

        //todo: make it async adding to a queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        "El cliente se creo satisfactoriamente.",
                        customer.getEmail()
                ));
    }
}
