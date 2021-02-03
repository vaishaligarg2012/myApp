package com.hindustantimes.SubscriptionPortal.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hindustantimes.SubscriptionPortal.models.Locality;
import com.hindustantimes.SubscriptionPortal.models.LocalityResponse;

@Component
public class LocalityResponseBuilder {


	public List<LocalityResponse> buildLocalityResponse(List<Locality> localityList){

		List<LocalityResponse> localityResponseList = new ArrayList<LocalityResponse>();
		if(localityList != null && !localityList.isEmpty()) {
			for (Locality locality : localityList){
				LocalityResponse localityResponse = new LocalityResponse();
				localityResponse.setId(locality.getId());
				localityResponse.setCity(locality.getCity());
				localityResponse.setState(locality.getState());
				localityResponse.setName(locality.getName());
				localityResponse.setPincode(locality.getPincode());
				localityResponseList.add(localityResponse);
			}
		}



		return localityResponseList;
	}
}
