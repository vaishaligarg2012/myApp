package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.CustomToken;
import com.hindustantimes.SubscriptionPortal.models.ExternalPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomTokenRepository extends JpaRepository<CustomToken, String> {

}
