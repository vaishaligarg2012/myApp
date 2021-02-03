package com.hindustantimes.SubscriptionPortal.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.Repository.OtpAdditionRepository;
import com.hindustantimes.SubscriptionPortal.models.OTPAddition;

@Service
public class OtpGenerateService {

	@Autowired
	SendOtpApiService sendOtpByService;

	@Autowired
	OtpAdditionRepository otpRepo;

	OTPAddition otpAddition = new OTPAddition();
	private static final Logger LOGGER = LogManager.getLogger(OtpGenerateService.class);

	public OTPAddition generateOtpAndAddToTable(String mobile, String source) {
		// Check if exisitng otp exists for this mobile and source and also is not
		// expired and is not consumed, before generating a new otp.

		OTPAddition hasOtp = this.getOTPDetails(mobile, source);

		if (hasOtp == null) {
			int otpMsg = genrateOtp();
			sendOtpByService.sendOtp(mobile, otpMsg,source);
			LOGGER.info(mobile + " " + otpMsg + " generateOtpAndAddToTable()");
			return this.addOTPDetails(otpMsg, mobile, source);
		} else {
			LOGGER.info(" Current Time "+ hasOtp.getGenerationTime() + " Expired Time  "+hasOtp.getExpiredTime()+ " recent generated "+LocalDateTime.now());
			LOGGER.info("OTP Not Expired");
			System.out.println(hasOtp.getOtp());
			sendOtpByService.sendOtp(mobile, hasOtp.getOtp(),source);
			return hasOtp;
		}

	}

	public int genrateOtp() {
		String numbers = "123456789";
		Random random = new Random();
		StringBuilder otp = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			otp.append(numbers.charAt(random.nextInt(numbers.length())));
		}
		LOGGER.info("Genrated OTP " + otp);
		return Integer.parseInt(otp.toString());
	}

	public OTPAddition resendOtp(String mobile, String ssp) {

		OTPAddition hasOtp = this.getOTPDetails(mobile, ssp);
		LOGGER.info("ResendOTP ", hasOtp.getMobileNum());

		if (hasOtp != null && !hasOtp.isConsumed()) {
			if (hasOtp.getExpiredTime().isBefore(LocalDateTime.now()) || hasOtp.isConsumed()) {
				LOGGER.info("OTP Expired  ");
				return generateOtpAndAddToTable(mobile, ssp);
			} else {
				LOGGER.info("OTP Not Expired");
				sendOtpByService.sendOtp(mobile, hasOtp.getOtp(),ssp);
				return hasOtp;
			}

		} else {
			return null;
		}

	}

	public OTPAddition getOTPDetails(String mobile, String source) {
		return otpRepo.findByMobileNum(mobile).stream().filter(otpRow -> !otpRow.getExpiredTime().isBefore(LocalDateTime.now()) && !otpRow.isConsumed() && source.equalsIgnoreCase(otpRow.getSource()))
				.findAny().orElse(null);
	}

	public OTPAddition addOTPDetails(int otpMsg, String mobile, String ssp) {
		otpAddition.setMobileNum(mobile);
		otpAddition.setOtp(otpMsg);
		otpAddition.setGenerationTime(LocalDateTime.now());
		otpAddition.setExpiredTime(LocalDateTime.now().plusHours(24));
		otpAddition.setSource(ssp);
		otpAddition.setApiCallSuccess(false);
		otpAddition.setConsumed(false);
		otpAddition.setVerified(false);
		OTPAddition result = otpRepo.save(otpAddition);
		LOGGER.info(result);
		return result;
	}
}
