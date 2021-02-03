package com.hindustantimes.SubscriptionPortal.Repository;

import com.hindustantimes.SubscriptionPortal.models.Coupons;
import com.hindustantimes.SubscriptionPortal.models.Order;
import com.hindustantimes.SubscriptionPortal.models.PublicationScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CouponRepository extends JpaRepository<Coupons, String>{

    List<Coupons> findBySapOrderNumberAndCouponNumber(String sapOrderNumber,String CouponNumber);
}
