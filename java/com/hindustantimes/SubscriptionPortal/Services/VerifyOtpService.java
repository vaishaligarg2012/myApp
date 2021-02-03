package com.hindustantimes.SubscriptionPortal.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.OtpAdditionRepository;
import com.hindustantimes.SubscriptionPortal.models.OTPAddition;

@Service
public class VerifyOtpService {

	@Autowired
	OtpAdditionRepository otpAdditionRepo;

	@Autowired
	AccountService accountService;
	private static final Logger LOGGER = LogManager.getLogger(VerifyOtpService.class);

	public boolean otpVerifiying(int otp, String mobile, String source) {
		OTPAddition otpInfo = otpAdditionRepo.findByMobileNum(mobile).stream()
				.filter(verifingOtp -> source.equalsIgnoreCase(verifingOtp.getSource()) && otp == verifingOtp.getOtp() && !verifingOtp.isConsumed() && !verifingOtp.getExpiredTime().isBefore(LocalDateTime.now()))
				.findAny().orElse(null);
		if (otpInfo != null) {
			LOGGER.warn(otpInfo.getMobileNum());
			LOGGER.warn("OTPVerifed......", otpInfo);
			Optional<OTPAddition> otpUsed = otpAdditionRepo.findById(otpInfo.getId());
			OTPAddition allInfo = otpUsed.get();
			allInfo.setConsumed(true);
			otpAdditionRepo.save(allInfo);
			return true;
		} else {
			return false;
		}
	}

}