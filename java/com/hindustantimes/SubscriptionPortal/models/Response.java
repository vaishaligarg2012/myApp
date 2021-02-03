package com.hindustantimes.SubscriptionPortal.models;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class Response<T> extends BaseResponse {

	private List<?> response;


}

