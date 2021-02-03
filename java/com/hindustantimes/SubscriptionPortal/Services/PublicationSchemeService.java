package com.hindustantimes.SubscriptionPortal.Services;
import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.PublicationSchemeRepository;
import com.hindustantimes.SubscriptionPortal.Repository.RecordTypeRepository;
import com.hindustantimes.SubscriptionPortal.models.PublicationScheme;
import com.hindustantimes.SubscriptionPortal.models.RecordType;
//import com.hindustantimes.SubscriptionPortal.models.NotExistingCustomer;

@Service
public class PublicationSchemeService {

	@Autowired 
	PublicationSchemeRepository schemeRepo;
	 
	@Autowired
	RecordTypeRepository recordRepo;
	
	@Value("${PRODUCT}")
	String sObjectType;
	
	@Value("${SCHEME_NAME_IN_RECORDTYPE}")
	String name;
 
	public List<PublicationScheme> getSchemesFromLocation(String salesOrg) {

		List<PublicationScheme> getSchemes = new ArrayList<>();
		getSchemes = this.getActiveSchemes();
		List<PublicationScheme> schemes = getSchemes.stream()
				  .filter(scheme -> salesOrg.equals(scheme.getSalesOrg().getTerritoryCode())).
				  collect(Collectors.toList());
				  
  		System.out.println(schemes);
		return schemes;
	}
	public List<PublicationScheme> getActiveSchemes(){
		List<PublicationScheme> getSchemes = new ArrayList<>();
//		RecordType scheme_record = recordRepo.findBysObjectTypeAndName(sObjectType, name);
		RecordType scheme_record = recordRepo.findSchemeRecord();
		String recordType = scheme_record.getId();
		getSchemes = schemeRepo.findByIsActiveAndRecordType(true, recordType);
		return getSchemes;
		
	}
}
