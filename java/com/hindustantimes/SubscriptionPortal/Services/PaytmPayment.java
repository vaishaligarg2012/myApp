package com.hindustantimes.SubscriptionPortal.Services;

import com.hindustantimes.SubscriptionPortal.Repository.LeadRepository;
import com.hindustantimes.SubscriptionPortal.Repository.PaymentRepository;
import com.hindustantimes.SubscriptionPortal.Repository.PublicationSchemeRepository;
import com.hindustantimes.SubscriptionPortal.models.Lead;
import com.hindustantimes.SubscriptionPortal.models.Payment;
import com.hindustantimes.SubscriptionPortal.models.PaymentGateway;
import com.hindustantimes.SubscriptionPortal.models.PublicationScheme;
import com.paytm.pg.merchant.PaytmChecksum;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.hindustantimes.SubscriptionPortal.common.Utils.getOldId;

@Service
public class PaytmPayment {
    // String amount

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    LeadRepository leadRepository;

    @Autowired
    PublicationSchemeRepository schemeRepository;

    @Value("${HEROKU_URL_REDIRECT}")
    String baseUrl;

    @Value("${PAYTM_URL}")
    String paytmURL;
    public String initiateTransaction(String linkId, String custId, String mobile, String email, String price, PaymentGateway paymentGateway) throws Exception {

        JSONObject paytmParams = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("requestType", "Payment");
        body.put("mid", paymentGateway.getMId());
        body.put("websiteName", "WEBSTAGING");
        body.put("orderId", linkId);
        String callbackUrl = baseUrl+"/apis/subscription/v1/reNewScheme/payments/paytmCallback";
        body.put("callbackUrl", callbackUrl);

        JSONObject txnAmount = new JSONObject();
        txnAmount.put("value", price);
        txnAmount.put("currency", "INR");

        JSONObject userInfo = new JSONObject();
        userInfo.put("custId", custId);
        userInfo.put("mobile", mobile);
        userInfo.put("email", email);
        body.put("txnAmount", txnAmount);
        body.put("userInfo", userInfo);


        String checksum = PaytmChecksum.generateSignature(body.toString(), paymentGateway.getKey());

        JSONObject head = new JSONObject();
        head.put("signature", checksum);

        paytmParams.put("body", body);
        paytmParams.put("head", head);

        String post_data = paytmParams.toString();
        String paymentInitiateURL = paytmURL+"?mid="+paymentGateway.getMId() + "&orderId=" + linkId;
        URL url = new URL(paymentInitiateURL);
        JSONObject returnTransaction = new JSONObject();
        String responseData = "";
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
            requestWriter.writeBytes(post_data);
            requestWriter.close();

            InputStream is = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
            if ((responseData = responseReader.readLine()) != null) {
                System.out.append("Response: " + responseData);
                JSONObject responseTrans = new JSONObject(responseData);
                returnTransaction = responseTrans.getJSONObject("body");

                // Create Payment object with Payment Link as url and payment mode as Online and payment status as null
            }
            responseReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return returnTransaction.getString("txnToken");
    }


    public boolean createPayment(String price, String linkId, PublicationScheme scheme, String leadId) {
        Payment payment = new Payment();
        System.out.println("lead id is: " + leadId);
        Optional<Lead> l = leadRepository.findById(leadId);
        Lead lead = l.get();
//        Optional<PublicationScheme> sch = schemeRepository.findById(schemeId);
//        PublicationScheme scheme = sch.get();
        payment.setAmount(Double.parseDouble(price));
        payment.setId(randomUUID());
        int oldId = getOldId();
        System.out.println(oldId);
        payment.setOldId(oldId);
        payment.setLinkId(linkId);
        payment.setLead(lead);
        payment.setScheme(scheme);
        payment.setPaymentMode("Online");
        payment.setPaymentStatus("Pending");
        payment.setCreatedDate(new Date());
        payment.setSchemeAmount(Double.parseDouble(price));
        System.out.println("payment heroku id: " + payment.getId());
        try {
            paymentRepository.save(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String randomUUID() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public Payment updatePaymentDetails(String linkId, String status, String txnID) {
        Optional<Payment> paymentOptional = paymentRepository.findByLinkId(linkId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setPaymentStatus(status.equals("TXN_SUCCESS") ?"Success": "Failed");
            payment.setLinkId(linkId);
            if(status.equals("TXN_SUCCESS")){
                payment.setGatewayTransactionId(txnID);
            }
            try {
                paymentRepository.save(payment);
                return payment;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Lead lead = payment.getLead();
                lead.setPayment(payment);
                leadRepository.save(lead);
            }
            catch (Exception e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    public String oldIdGenerate() {
        String numbers = "123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }


}
