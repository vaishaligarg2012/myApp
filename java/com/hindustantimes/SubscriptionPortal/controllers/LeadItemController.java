package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Repository.LeadRepository;
import com.hindustantimes.SubscriptionPortal.Services.AddLeadService;
import com.hindustantimes.SubscriptionPortal.Services.LeadService;
import com.hindustantimes.SubscriptionPortal.models.BaseResponse;
import com.hindustantimes.SubscriptionPortal.models.OrderRequest;
import com.hindustantimes.SubscriptionPortal.models.UpdateOrderRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = { "${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}" })
@RequestMapping(value = "${hindustan-subscription.api.version}/order")
public class LeadItemController {

	@Autowired
	LeadService leadService;

	@Autowired
	AddLeadService addLeadService;

	@Autowired
	LeadRepository leadRepository;
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    List<String> errors = new ArrayList<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {

	        String errorMessage = error.getDefaultMessage();
	        errors.add(errorMessage);
	    });
	    return errors;
	}
	
	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest lead, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			BaseResponse response =new BaseResponse();
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<String> message = new ArrayList<>();
			response.setSuccess(false);
			for (FieldError e : errors) {
				message.add( e.getField() + "-" + e.getDefaultMessage());
			}
			response.setMessage(message.toString());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		System.out.println("inside create lead");
		BaseResponse<?> response = leadService.createLead(lead);
		if(response.isSuccess())
		{
			JSONObject leadResp = addLeadService.addlead(lead, "");
			JSONObject resp = new JSONObject();
			String leadId = leadResp.has("lead_id") ? leadResp.getString("lead_id") : "";
			if(leadId.isEmpty()){
				resp.put("success", false);
				resp.put("message", "Some error occurred");
				resp.put("order_id", leadId);
				return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST);
			}
			else {
				resp.put("success", response.isSuccess());
				resp.put("message", response.getMessage());
				resp.put("order_id", leadId);
				return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
			}

		}
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}


	@PostMapping("/update-order")
	public ResponseEntity<?> updateOrder(@Valid @RequestBody UpdateOrderRequest updateOrder, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			BaseResponse<?> response =new BaseResponse<>();
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<String> message = new ArrayList<>();

			response.setSuccess(false);
			for (FieldError e : errors) {
				message.add( e.getField() + "-" + e.getDefaultMessage());
			}
			response.setMessage(message.toString());
			return new ResponseEntity<BaseResponse<?>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		System.out.println("inside update lead");
		BaseResponse resp = leadService.updateAddress(updateOrder);
		if(resp.isSuccess())
			return new ResponseEntity<>(resp, HttpStatus.OK);
		return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
	}



}
