package com.amigoscode.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

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

        FraudCheckReponse fraudCheckReponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckReponse.class,
                customer.getId());

        if ( fraudCheckReponse.isFrauster() ) {
            throw new IllegalStateException("Fraudster");
        }


        //todo: send notification
    }
}
