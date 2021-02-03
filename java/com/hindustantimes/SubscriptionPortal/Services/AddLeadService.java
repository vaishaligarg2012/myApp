package com.hindustantimes.SubscriptionPortal.Services;
import java.util.Collections;
//import java.util.Optional;
//import java.util.function.Predicate;

import com.hindustantimes.SubscriptionPortal.models.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;

@Service
public class AddLeadService {

	@Autowired
	AccountRepository accrepo;

	@Autowired
	AccountService accountService;

	@Autowired
	CirculationAuthService circulationauthservice;

	@Value("${CIRCULATION_URL}")
	String circulationUrl;

	public JSONObject addlead(OrderRequest request, String bookingType) throws JSONException {
		
		AddLeadRequest outgoingRequest = new AddLeadRequest();
		if(request.getSource().equals(new String("SSP")))
		{

			Account account = accountService.getRegisteredUsersByMobile(request.getMobile());
			outgoingRequest.setCustomer_name(account.getName());
			outgoingRequest.setMob(account.getMobileNumber());
			outgoingRequest.setEmail(account.getEmail());
			outgoingRequest.setFlat_no(account.getFlatNum());
			if(account.getLocality() != null)
				outgoingRequest.setLocality(account.getLocality().getId());
			if(account.getSociety() != null)
				outgoingRequest.setSociety(account.getSociety().getId());
			outgoingRequest.setFloor(account.getFloor());
			outgoingRequest.setBlock_or_street(account.getBlockOrStreet());
			outgoingRequest.setSociety_non_master(account.getSocietyNonMaster());
			outgoingRequest.setCity_non_master("");
			outgoingRequest.setType_of_booking(bookingType.isEmpty()?"2":((bookingType.equals("Renewal"))?"2": "3"));
			outgoingRequest.setArea(account.getArea());
			outgoingRequest.setCity(account.getCity());
			outgoingRequest.setPin_code(account.getPinCode());
			outgoingRequest.setLandline(account.getLandLine());
			outgoingRequest.setAccount(account.getId());
			outgoingRequest.setOrder(request.getOrder());
			outgoingRequest.setLead_id(request.getLead_id());

			
		}
		else {
			outgoingRequest.setCustomer_name(request.getCustomerName());
			outgoingRequest.setMob(request.getMobile());
			outgoingRequest.setEmail(request.getEmail());
			outgoingRequest.setFlat_no(request.getFlatNo());
			outgoingRequest.setLocality(request.getLocality());
			outgoingRequest.setLocality_non_master(request.getLocalityNonMaster());
			outgoingRequest.setFloor(request.getFloor());
			outgoingRequest.setBlock_or_street(request.getBlockOrStreet());
			outgoingRequest.setSociety(request.getSociety());
			outgoingRequest.setSociety_non_master(request.getSocietyNonMaster());
			outgoingRequest.setCity_non_master(request.getCityNonMaster());
			outgoingRequest.setType_of_booking("1");
			outgoingRequest.setArea(request.getArea());
			outgoingRequest.setCity(request.getCity());
			outgoingRequest.setPin_code(request.getPinCode());
			outgoingRequest.setLandline("");
			outgoingRequest.setStep("last");
			outgoingRequest.setDigitalOrderId(request.getDigitalOrderId());
			outgoingRequest.setDigitalCustomerId(request.getCustomerUniqueId());
			
		}
		outgoingRequest.setSource(request.getSource());
		outgoingRequest.setChannel("");
		outgoingRequest.setRemarks_non_master("");
		outgoingRequest.setIs_spot_or_gift("no");
		outgoingRequest.setPublication(request.getPublication());
		outgoingRequest.setWith_vendor("0");
		outgoingRequest.setLat("");
		outgoingRequest.setLng("");
		outgoingRequest.setMicr_no("");
		outgoingRequest.setAge_group("");
		outgoingRequest.setMicr_no("");
		outgoingRequest.setOld_coupon_number("");
		outgoingRequest.setSchemes(request.getScheme());
		outgoingRequest.setCheque_date("");
		outgoingRequest.setOtp("");
		outgoingRequest.setReading_interest("");
		outgoingRequest.setProfession_occupation("");
		outgoingRequest.setPayment_mode_no(request.getTransactionId());
		outgoingRequest.setSignature("");
		outgoingRequest.setSales_org(request.getSalesOrg());
		outgoingRequest.setIs_signature_mandatory("false");
		outgoingRequest.setBank_acc_no("");
		outgoingRequest.setAmount((String.valueOf(request.getAmount())));
		outgoingRequest.setPayment_mode("Online");
		outgoingRequest.setQuantity("1");
		outgoingRequest.setBelongs_to_vendor("");
		outgoingRequest.setBelongs_to_main_center("");
		outgoingRequest.setIs_interested("1");
		outgoingRequest.setIs_vendor_booking("false");
		outgoingRequest.setAge_group("");
		outgoingRequest.setManual_form_no("");
		outgoingRequest.setRequest_id("");
		outgoingRequest.setRemarks("");
		CirculationAuthToken token = circulationauthservice.genereateToken();
		String authToken;
		HttpHeaders headers = new HttpHeaders();
		if(token.getData().containsKey("token"))
			authToken = token.getData().get("token");
		else
			return null;
		String headervalue = "Bearer " + authToken;
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", headervalue);

		HttpEntity<AddLeadRequest> entity = new HttpEntity<>(outgoingRequest, headers);
		
		String apiUrl = circulationUrl + "/circulation/mre/api/add-lead/";
//		String tokenUrl = circulationUrl + ""
//		apiUrl = "https://circulation-heroku.herokuapp.com/circulation/mre/api/add-lead/";
//		apiUrl = "http://127.0.0.1:8081/circulation/mre/api/add-lead/";
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<?> response= restTemplate.postForEntity(apiUrl, entity, String.class);

		JSONObject resp = new JSONObject(response.getBody().toString());
		return resp;

//		return resp.has("lead_id") ? resp.getString("lead_id"): "";

	}

}
