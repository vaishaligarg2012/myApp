package com.hindustantimes.SubscriptionPortal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.hindustantimes.SubscriptionPortal.models.PublicationScheme;
import com.hindustantimes.SubscriptionPortal.models.RenewalMapping;

public interface RenewalMappingRepository extends JpaRepository<RenewalMapping, Long>{
	
//	@EntityGraph(value = "RenewalMapping.oldScheme", type = EntityGraphType.FETCH)
	List<RenewalMapping> findByOldSchemeId(String id);

}
