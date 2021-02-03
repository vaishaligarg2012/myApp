package com.hindustantimes.SubscriptionPortal.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hindustantimes.SubscriptionPortal.Services.FetchLocalityService;
import com.hindustantimes.SubscriptionPortal.models.LocalityResponse;
import com.hindustantimes.SubscriptionPortal.models.Response;

@RestController
@CrossOrigin(origins = { "${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}" })
@RequestMapping(value = "${hindustan-subscription.api.version}")
public class LocalityController {

	@Autowired
	FetchLocalityService fetchLocalityService;

	private static final Logger LOGGER = LogManager.getLogger(LoginUserController.class);

	@GetMapping("/get-localities")
	public ResponseEntity<?> getLocalitiesBySalesOrgCity(
			@RequestParam(value = "sales_org") String sales_org, @RequestParam(value = "city") String city) {
		Response<LocalityResponse> response = new Response<LocalityResponse>();
		LOGGER.warn(sales_org + " " + city);
		if  (!sales_org.equals("")) {
			List<LocalityResponse> localityInfo = fetchLocalityService.fetchLocalityBySales_Org(sales_org, city);
			if (localityInfo == null || localityInfo.isEmpty()) {
				response.setMessage("No matched found");
				response.setSuccess(false);
				return new ResponseEntity<Response<LocalityResponse>>(response,HttpStatus.BAD_REQUEST);
			} else {
				response.setResponse(localityInfo);
				response.setMessage("Data Fetch");
				response.setSuccess(true);
				return new ResponseEntity<Response<LocalityResponse>>(response, HttpStatus.OK);
			}
		} else {
			
			response.setMessage("something is missing salesorg id");
			response.setSuccess(false);
			return new ResponseEntity<Response<LocalityResponse>>(response,HttpStatus.BAD_REQUEST);
		}
	}
}