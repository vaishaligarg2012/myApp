package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.CouponValidity;
import com.hindustantimes.SubscriptionPortal.models.ExternalPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExternalPartnerRepository extends JpaRepository<ExternalPartner, String> {

    Optional<ExternalPartner> findByLocation_idAndMobileAndIsActiveAndType(String locationId, String mobile, Boolean isActive,String type);
}
