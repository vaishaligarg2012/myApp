package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.CouponSchedule;
import com.hindustantimes.SubscriptionPortal.models.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponScheduleRepository extends JpaRepository<CouponSchedule, String> {

    List<CouponSchedule> findByScheme_id(String scheme_id);
}
