package com.hindustantimes.SubscriptionPortal.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.hindustantimes.SubscriptionPortal.models.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendOtpApiService {
	@Value("${SMS_USERNAME}")
	String userName;

	@Value("${SMS_PASSWORD}") 
	String password;
	
	@Value("${SMS_API_URL}")
	String API_URL;
	
	private static final Logger LOGGER = LogManager.getLogger(SendOtpApiService.class);

	public String sendOtp(String mobileNumber, int msg,String source) {
		try {
			String data = "";
			source = source.toLowerCase();
			if(!Constants.SMS_TEXT.containsKey(source)){
				return new String("Enter a valid source");
			}
			String text = Constants.SMS_TEXT.get(source);
			String messageText = String.format(text,msg);
			data += "method=sendMessage";
			data += "&userid=" + userName; 
			data += "&password=" + URLEncoder.encode(password, "UTF-8"); 
			data += "&msg=" + URLEncoder.encode(messageText, "UTF-8");
			data += "&send_to=" + URLEncoder.encode(mobileNumber, "UTF-8"); 
			data += "&v=1.1";
			data += "&msg_type=TEXT"; 
			data += "&auth_scheme=PLAIN";
			LOGGER.info("sendOtp() getting data " ,data);
			URL url = new URL(API_URL + data);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.connect();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				buffer.append(line).append("\n");
			}
			LOGGER.info(buffer.toString());
			rd.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
