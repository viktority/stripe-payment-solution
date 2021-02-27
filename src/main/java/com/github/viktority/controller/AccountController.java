package com.github.viktority.controller;

import com.github.viktority.services.AccountService;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    String accountId = null;

    @GetMapping("/account/refreshurl")
    public String refreshUrl(Model model) throws StripeException {
        AccountLink accountLink = accountService.createAccountLink(accountId);
        return "redirect:" + accountLink.getUrl();
    }

    @GetMapping("/account/successful")
    public String created(Model model) {
        model.addAttribute("accountId", accountId);
        return "successful";
    }


    @GetMapping("/account/create")
    public String createAccount(Model model) {
        try {
            Account connectedAccount = accountService.createConnectedAccount();
            accountId = connectedAccount.getId();
            AccountLink accountLink = accountService.createAccountLink(connectedAccount.getId());
            return "redirect:" + accountLink.getUrl();
        } catch (Exception e) {
        }
        return "redirect: /";
    }

    @GetMapping("/account/signup")
    public String accountPage(Model model) {
        return "createaccount";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
