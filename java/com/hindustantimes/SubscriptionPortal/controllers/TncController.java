package com.hindustantimes.SubscriptionPortal.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TncController {
    @Value("${HEROKU_URL_REDIRECT}")
    String baseUrl;
    private final static String TNC ="/tnc";
    private final static String redeemed="/redeem";

    @RequestMapping(TNC)
    public void getTnc(HttpServletResponse httpResponse) throws IOException {
        String redirectUrl = baseUrl + "?msg=tnc";
        httpResponse.sendRedirect(redirectUrl);

    }
    @RequestMapping(redeemed)
    public void getRedeem(HttpServletResponse httpResponse) throws IOException {
        String redirectUrl = baseUrl + "?msg=redeem";
        httpResponse.sendRedirect(redirectUrl);

    }

}
