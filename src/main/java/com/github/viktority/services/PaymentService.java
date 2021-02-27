package com.github.viktority.services;


import com.github.viktority.models.Item;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @Value("${return_url}")
    private String returnUrl;

    @Value("${cancel_url}")
    private String cancelUrl;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Session createSession(Item item, String destinationAccount) throws StripeException {
        Map<String, Object> params = new HashMap<>();

        ArrayList<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        params.put("payment_method_types", paymentMethodTypes);

        ArrayList<String> images = new ArrayList<>();
        images.add(item.getImage());

        ArrayList<HashMap<String, Object>> lineItems = new ArrayList<>();
        HashMap<String, Object> lineItem = new HashMap<>();
        lineItem.put("name", item.getName());
        lineItem.put("images", images);
        lineItem.put("amount", item.getAmount() * 100);
        lineItem.put("currency", item.getCurrency());
        lineItem.put("quantity", item.getQuantity());
        lineItems.add(lineItem);
        params.put("line_items", lineItems);

        HashMap<String, Object> paymentIntentData = new HashMap<>();
        paymentIntentData.put("application_fee_amount", computeApplicationFeeAmount(item.getAmount(), item.getQuantity()));
        HashMap<String, Object> transferData = new HashMap<>();
        // The account receiving the funds, as passed from the client.
        transferData.put("destination", destinationAccount);
        paymentIntentData.put("transfer_data", transferData);
        params.put("payment_intent_data", paymentIntentData);

        // ?session_id={CHECKOUT_SESSION_ID} means the redirect will have the session ID set as a query param
        params.put("success_url", returnUrl);
        params.put("cancel_url", cancelUrl);

        return Session.create(params);
    }

    private static int computeApplicationFeeAmount(Long basePrice, Long quantity) {
        return (int) (basePrice * quantity * 0.1);
    }

}
