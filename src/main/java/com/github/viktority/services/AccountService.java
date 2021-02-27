package com.github.viktority.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.param.AccountLinkCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @Value("${refresh_url}")
    private String refreshUrl;

    @Value("${accountcreated_url}")
    private String accountcreatedUrl;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Account createConnectedAccount() throws StripeException {
        Map<String, Object> cardPayments =new HashMap<>();
        cardPayments.put("requested", true);
        Map<String, Object> transfers = new HashMap<>();
        transfers.put("requested", true);
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("card_payments", cardPayments);
        capabilities.put("transfers", transfers);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "express");
        params.put("country", "GB");
        params.put("capabilities", capabilities);

        return Account.create(params);
    }


    public AccountLink createAccountLink(String accountId) throws StripeException {
        AccountLinkCreateParams params = AccountLinkCreateParams.builder()
                .setAccount(accountId)
                .setRefreshUrl(refreshUrl)
                .setReturnUrl(accountcreatedUrl)
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();

        return AccountLink.create(params);
    }
}
