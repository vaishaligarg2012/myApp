package com.hindustantimes.SubscriptionPortal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hindustantimes.SubscriptionPortal.models.Society;

@Repository
public interface SocietyRepository extends JpaRepository<Society, String>{
	
	 List<Society> findByLocalityHerokuIdAndIsActive(String salesOrg, boolean isActive); 

}
 