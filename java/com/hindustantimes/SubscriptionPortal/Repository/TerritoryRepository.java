package com.hindustantimes.SubscriptionPortal.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hindustantimes.SubscriptionPortal.models.Territory;

/**
 * Created by Vaishali Garg.
 */

@Repository
public interface TerritoryRepository extends CrudRepository<Territory, String>{
	    
	    Territory findOneByTerritoryCode(String territoryCode);

} 
 