package com.mmabas77.backend.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StripeService {

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(StripeService.class);

    @Autowired
    private String stripeKey;

    public String createCustomer(Map<String, Object> tokenParams,
                                 Map<String, Object> customerParams) {

        Stripe.apiKey = stripeKey;

        String stripeCustomerId = null;
        try {
            Token token = Token.create(tokenParams);
            customerParams.put("source", token.getId());
            Customer customer = Customer.create(customerParams);
            stripeCustomerId = customer.getId();
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return stripeCustomerId;
    }
}
