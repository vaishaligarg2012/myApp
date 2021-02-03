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

import com.hindustantimes.SubscriptionPortal.Services.SocietyService;
import com.hindustantimes.SubscriptionPortal.models.Response;
import com.hindustantimes.SubscriptionPortal.models.SocietyResponse;

@RestController
@CrossOrigin(origins = { "${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}" })
@RequestMapping(value = "${hindustan-subscription.api.version}")
public class SocietyController {

	@Autowired
	SocietyService societyService;
	private static final Logger LOGGER = LogManager.getLogger(LoginUserController.class);

	@GetMapping("/get-societies")
	public ResponseEntity<?> getSocietyByLocality(@RequestParam(value = "locality_id") String localityId) {

		Response<SocietyResponse> response = new Response<SocietyResponse>();
		LOGGER.info("Locality " + localityId);

		if (localityId.equals("")) {
			response.setMessage("Enter id");
			response.setSuccess(false);
			return new ResponseEntity<Response<SocietyResponse>>(response, HttpStatus.BAD_REQUEST);
		} else {
			List<SocietyResponse> societyList = societyService.fetchSocietyByLocalityHerokuId(localityId);
			if (societyList == null || societyList.isEmpty()) {
				response.setMessage("No record found");
				response.setSuccess(false);
				return new ResponseEntity<Response<SocietyResponse>>(response, HttpStatus.BAD_REQUEST);
			} else {
				response.setResponse(societyList);
				response.setMessage("Records");
				response.setSuccess(true);
				return new ResponseEntity<Response<SocietyResponse>>(response, HttpStatus.OK);
			}

		} 

	}
}
