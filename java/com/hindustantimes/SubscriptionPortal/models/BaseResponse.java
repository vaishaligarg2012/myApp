package com.hindustantimes.SubscriptionPortal.models;


import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class BaseResponse<T> {

    private boolean success;
    private String message;
}
