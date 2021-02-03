package com.hindustantimes.SubscriptionPortal.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hindustantimes.SubscriptionPortal.models.PublicationScheme;
@Repository
public interface PublicationSchemeRepository extends JpaRepository<PublicationScheme, String>{
	
//	@Autowired
//	RecordTypeRepository recordTypeRepo;
//	
	
//	@Query("select * from product2 where isactive = true")
	List<PublicationScheme> findByIsActiveAndRecordType(boolean isActive,String recordType);
	PublicationScheme findByCode(String code);
	List<PublicationScheme> findByIsActiveAndChannelContains(boolean isActive,String channel);

}
