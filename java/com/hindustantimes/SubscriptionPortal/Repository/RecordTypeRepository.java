package com.hindustantimes.SubscriptionPortal.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hindustantimes.SubscriptionPortal.models.RecordType;
@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long>{
//	public final String product;
//	@Value("${PRODUCT}") 
//	String product;
//	
//	
//	@Value("${SCHEME_NAME_IN_RECORDTYPE}")
//	String scheme;
//	String product = "Product2";
//	String scheme = "HT_Scheme";
	
	String query = "select * from recordtype where sobjecttype = 'Product2'  and developername = 'HT_Scheme'";
	@Query(value=query,nativeQuery = true)
	RecordType findSchemeRecord();
//	RecordType findBysObjectTypeAndName(String sObjectType,String name);
//	RecordType findBysObjectTypeAndName();
}
