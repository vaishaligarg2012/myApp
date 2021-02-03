package com.hindustantimes.SubscriptionPortal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hindustantimes.SubscriptionPortal.models.Locality;

@Repository

public interface LocalityRepository extends JpaRepository<Locality, String> {

	List<Locality> findBySalesOrgHerokuId(String salesOrgHerokuId);

} 