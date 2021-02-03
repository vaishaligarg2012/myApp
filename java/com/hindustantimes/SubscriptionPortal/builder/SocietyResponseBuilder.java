package com.hindustantimes.SubscriptionPortal.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hindustantimes.SubscriptionPortal.models.Society;
import com.hindustantimes.SubscriptionPortal.models.SocietyResponse;


@Component
public class SocietyResponseBuilder {


	public List<SocietyResponse> buildSocietyResponse(List<Society> societyList){

		List<SocietyResponse> societyResponseList = new ArrayList<SocietyResponse>();
		if(societyList != null && !societyList.isEmpty()) {
			for (Society locality : societyList){
				SocietyResponse localityResponse = new SocietyResponse();
				localityResponse.setId(locality.getId());
				localityResponse.setName(locality.getName());
				societyResponseList.add(localityResponse);
			}
		}    



		return societyResponseList;
	}
}
