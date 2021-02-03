package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.CouponSchedule;
import com.hindustantimes.SubscriptionPortal.models.CouponValidity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponValidityRepository extends JpaRepository<CouponValidity, String> {

    CouponValidity findByLocation_id(String locationId);
}
