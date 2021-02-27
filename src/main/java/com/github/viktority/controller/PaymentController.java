package com.github.viktority.controller;


import com.github.viktority.models.Item;
import com.github.viktority.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Value("${destination_account}")
    String destinationAccountId;// = "acct_1IOm86QMsXLFfi8y";

    @Value("${image.file.url.1}")
    private String image1;

    @Value("${image.file.url.2}")
    private String image2;

    @GetMapping("/payment/purchaseitem")
    public String createSession(Model model) throws StripeException {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Item item = generateRandomItem();
            Session session = paymentService.createSession(item, destinationAccountId);
            item.setClientSecret(session.getId());
            items.add(item);
        }
        model.addAttribute("items", items);
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "checkout";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getStripeError());
        return "result";
    }


    @GetMapping("/payment/successfulpayment")
    public String returnUrl(Model model) {
        return "successfulpayment";
    }


    @GetMapping("/payment/unsuccessfulpayment")
    public String cancelUrl(Model model) {
        return "unsuccessfulpayment";
    }

    private Item generateRandomItem() {
        int amount = random(100, 200);
        String name = "Item" + amount;
        int v = random(1, 2);
        //String image = v == 2 ? image2 : image1;
        return new Item(name, amount, Item.Currency.EUR.toString(), image2, 1);
    }

    private static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

}
