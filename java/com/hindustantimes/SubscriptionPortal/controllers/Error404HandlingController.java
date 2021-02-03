package com.hindustantimes.SubscriptionPortal.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Error404HandlingController implements ErrorController {
    private final static String PATH = "/error";


    @RequestMapping(PATH)
    @ResponseBody
    public String getErrorPath(HttpServletResponse httpResponse) throws IOException {
        // redirect to home page if page not found

        httpResponse.sendRedirect("/");
        return null;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}


