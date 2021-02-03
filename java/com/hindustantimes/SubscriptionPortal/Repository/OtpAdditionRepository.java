package com.hindustantimes.SubscriptionPortal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hindustantimes.SubscriptionPortal.models.OTPAddition;

import java.util.List;


@Repository
public interface OtpAdditionRepository extends JpaRepository<OTPAddition,Long> {

      List<OTPAddition> findByMobileNum(String mobileNum);
	
}
 