package com.hindustantimes.SubscriptionPortal.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.LocalityRepository;
import com.hindustantimes.SubscriptionPortal.Repository.SocietyRepository;
import com.hindustantimes.SubscriptionPortal.Repository.TerritoryRepository;
import com.hindustantimes.SubscriptionPortal.builder.LocalityResponseBuilder;
import com.hindustantimes.SubscriptionPortal.models.Locality;
import com.hindustantimes.SubscriptionPortal.models.LocalityResponse;
import com.hindustantimes.SubscriptionPortal.models.Territory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class FetchLocalityService {

	@Autowired
	private TerritoryRepository territoryRepo;

	@Autowired
	LocalityRepository localityRepo;

	@Autowired
	SocietyRepository societyRepo;

	@Autowired
	LocalityResponseBuilder localityResponseBuilder;

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	public List<LocalityResponse> fetchLocalityBySales_Org(String territoryCode, String city) {
		if (territoryCode.isEmpty()) {
			return null;
		} else {
			Territory salesOrgId = territoryRepo.findOneByTerritoryCode(territoryCode);
			if (salesOrgId != null) {
				LOGGER.info("FetchLocality " + salesOrgId.getId());
				List<Locality> localityInfo = localityRepo.findBySalesOrgHerokuId(salesOrgId.getId());
				if(city != null && !city.isEmpty())
					localityInfo = localityInfo.stream().filter(locality -> locality.getCity() != null && locality.getCity().toLowerCase().equals(city.toLowerCase())).collect(Collectors.toList());

				List<LocalityResponse> localityResponseList = localityResponseBuilder
						.buildLocalityResponse(localityInfo);
 
				return localityResponseList;
			} else {
				return null;
			}

		}
	}

	void fetchSocietyBy(Locality localityId) {

	}
}
