package com.amigoscode.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {

    private final FraudCheckService fraudCheckService;

    /* Como estasmos usando lombok, podemos usar la anotacion AllArgsConstructor
    public FraudController(FraudCheckService fraudCheckService) {
        this.fraudCheckService = fraudCheckService;
    }
     */

    @GetMapping(path = "{customerId}")
    public FraudCheckReponse isFrauster(@PathVariable("customerId") Integer customerId){
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        log.info("Fraud check request for customer {}", customerId);
        return new FraudCheckReponse(isFraudulentCustomer);
    }

}