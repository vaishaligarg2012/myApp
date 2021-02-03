package com.hindustantimes.SubscriptionPortal.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.SocietyRepository;
import com.hindustantimes.SubscriptionPortal.builder.SocietyResponseBuilder;
import com.hindustantimes.SubscriptionPortal.models.Society;
import com.hindustantimes.SubscriptionPortal.models.SocietyResponse;

@Service
public class SocietyService {

	@Autowired
	SocietyRepository societyRepo;

	@Autowired
	SocietyResponseBuilder societyResponseBuilder;
	public List<SocietyResponse> fetchSocietyByLocalityHerokuId(String localityHerokuId) {

		List<Society> societyList = societyRepo.findByLocalityHerokuIdAndIsActive(localityHerokuId, true);
		List<SocietyResponse> societyResponseList = societyResponseBuilder.buildSocietyResponse(societyList);
		if (societyResponseList == null || societyResponseList.isEmpty()) {
			return null;
		} else {
			return societyResponseList;
            
		} 
	}
} 
