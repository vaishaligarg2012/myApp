package com.hindustantimes.SubscriptionPortal.controllers;

import com.hindustantimes.SubscriptionPortal.Repository.LeadRepository;
import com.hindustantimes.SubscriptionPortal.Repository.OrderRepository;
import com.hindustantimes.SubscriptionPortal.Repository.PaymentRepository;
import com.hindustantimes.SubscriptionPortal.Repository.PublicationSchemeRepository;
import com.hindustantimes.SubscriptionPortal.Services.*;
import com.hindustantimes.SubscriptionPortal.builder.PaytmPaymentBuilder;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"${hinsustan-subscription.cross.origin}", "${hinsustan-subscription.cross.origin.backend}"})
@RequestMapping(value = "${hindustan-subscription.api.version}" + "/reNewScheme/payments")
class PaymentController {

    @Autowired
    PaytmPayment paytmPayment;

    @Autowired
    CirculationAuthService circulationauthservice;

    @Autowired
    AddLeadService leadService;

    @Autowired
    AccountService accountService;

    @Value("${CIRCULATION_URL}")
    String circulationUrl;

    @Autowired
    PaytmPaymentBuilder paytmPaymentBuilder;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PublicationSchemeRepository schemeRepository;

    @Value("${HEROKU_URL_REDIRECT}")
    String baseUrl;
    @Value("${is_prod}")
    boolean isProd;
    @Autowired
    CreateLeadService createLeadService;

    @Autowired
    LeadRepository leadRepository;

    @Autowired
    OrderRepository orderRepo;
    private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

    @Autowired
    PaymentGatewayDetails paymentGatewayDetails;
    String territoryId = null;
    String publication = null;
    String paymentMode = null;
    boolean isActive;

    @GetMapping("/fetchAllPayment/{linkId}/{mobile}/{price}/{schemeId}/{orderId}")
    public ResponseEntity<?> fetchPaymentInfo(@PathVariable String linkId, @PathVariable String mobile,
                                              @PathVariable String price, @PathVariable String orderId,
                                              @PathVariable String schemeId) throws Exception {

        //paymentGatewayDetails.getPaymentGatewayDetails()
        System.out.println("order id is: " + orderId);
        System.out.println("Price is " + price);
        Optional<PublicationScheme> sch = schemeRepository.findById(schemeId);
        PublicationScheme scheme = sch.get();

        if (isProd == true) {
            territoryId = scheme.getSalesOrg().getId();
            isActive = true;
        }
        publication = scheme.getPublications().split(";")[0];
        paymentMode = "Online";


        PaymentGateway paymentGateway = paymentGatewayDetails.getPaymentGatewayDetails(territoryId, publication, paymentMode, isActive);
        Account account = accountService.getRegisteredUsersByMobile(mobile);
        CheckoutResponse resp = new CheckoutResponse();
        if (scheme.getSource() != null && scheme.getSource().equals("Digital") && (account.getEmail() == null || account.getEmail().isEmpty())) {
            resp.setSuccess(false);
            resp.setMessage("Email is mandatory for selected scheme");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        PaytmDetails paytmData = paytmPaymentBuilder.initializedPaymentDetails(linkId, account, paymentGateway);
        String allTokenInfo = paytmPayment.initiateTransaction(paytmData.getOrderID(),
                paytmData.getCustId(), paytmData.getMobile(), paytmData.getEmail(), price, paymentGateway);

        CirculationAuthToken token = circulationauthservice.genereateToken();
        String authToken = null;
        HttpHeaders headers = new HttpHeaders();
        if (token.getData().containsKey("token"))
            authToken = token.getData().get("token");
//        else
//            return ;
        String headervalue = "Bearer " + authToken;
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", headervalue);
        AddRenewalLeadRequest outgoingRequest = new AddRenewalLeadRequest();
        outgoingRequest.setOrderId(orderId);
        Order order = orderRepo.findById(orderId).get();
        int daysRemains = 0;
        try {
            daysRemains = (int) Double.parseDouble(order.getDaysRemaining());
        } catch (Exception e) {
//            LOGGER.warn("Number format " + e);
            try{
                daysRemains = Integer.parseInt(order.getDaysRemaining());
            }
            catch (Exception ex){
                LOGGER.warn("Number format " + ex);
            }
        }
        if (daysRemains < -90)
            outgoingRequest.setTypeOfBooking("3");
        else
            outgoingRequest.setTypeOfBooking("2");

        outgoingRequest.setSource("SSP");

        HttpEntity<AddRenewalLeadRequest> entity = new HttpEntity<>(outgoingRequest, headers);

        String apiUrl = circulationUrl + "/circulation/mre/api/add-renewal-lead/";
//		String tokenUrl = circulationUrl + ""
        apiUrl = circulationUrl + "/circulation/mre/api/add-renewal-lead/";
//        apiUrl = "http://127.0.0.1:8081/circulation/mre/api/add-renewal-lead/";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<?> response = restTemplate.postForEntity(apiUrl, entity, AddRenewalLeadResponse.class);
        AddRenewalLeadResponse body = (AddRenewalLeadResponse) response.getBody();
        String leadId = "";
        if (body.isSuccess()) {
            leadId = (String) body.getData().get("id");
        }
        System.out.println(response);

        boolean status = paytmPayment.createPayment(price, linkId, scheme, leadId);

        if (status) {

        }
        resp.setSuccess(true);
        resp.setMessage("OK");
        resp.setTxnToken(allTokenInfo);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/paytmCallback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handleGet(@RequestParam MultiValueMap<String, String> paymentTxnInfo, HttpServletResponse response) throws JSONException, IOException {
        System.out.println(paymentTxnInfo.get("ORDERID") + " " + paymentTxnInfo.get("BANKTXNID") + paymentTxnInfo.get("STATUS"));
        String linkId = paymentTxnInfo.get("ORDERID").toString().replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String status = paymentTxnInfo.get("STATUS").toString().replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String txnID = paymentTxnInfo.get("BANKTXNID").toString().replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String bookingFormNo = "";
        if (status.equals("TXN_SUCCESS")) {
            Payment payment = paytmPayment.updatePaymentDetails(linkId, status, txnID);
            OrderRequest request = new OrderRequest();
            if (payment != null) {
                Lead lead = payment.getLead();
                request.setLead_id(lead.getId());
                request.setSource("SSP");
                request.setMobile(lead.getMobile());
                request.setOrder(lead.getOrder().getId());
                request.setScheme(payment.getScheme().getId());
                request.setPublication(payment.getScheme().getPublications());
                request.setTransactionId(payment.getGatewayTransactionId());
                request.setSalesOrg(lead.getLocation().getId());
                request.setAmount(payment.getScheme().getPrice());
                request.setAddressFilled(false);

                JSONObject leadResp = leadService.addlead(request, lead.getBookingType());
                String leadId = leadResp.has("lead_id") ? leadResp.getString("lead_id") : "";
                bookingFormNo = leadResp.has("booking_form_no") ? leadResp.getString("booking_form_no") : bookingFormNo;
            }
        }
        String redirectUrl = baseUrl + "?status=" +  ((bookingFormNo == null || bookingFormNo.isEmpty()) ? "TXN_FAILED" : status);
        redirectUrl = (bookingFormNo == null || bookingFormNo.isEmpty())? redirectUrl: (redirectUrl + "&ref="+ bookingFormNo);
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/getPaymentByLinkId/{linkId}")
    public ResponseEntity<?> getPaymentInfoByLinkId(@PathVariable String linkId) {
        Optional<Payment> paymentOptional = paymentRepository.findByLinkId(linkId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            return new ResponseEntity<>(payment, HttpStatus.OK);

        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //String territoryId, String publication, String paymentMode, boolean isActive
    @GetMapping("/getGatewayInfo/{territoryId}/{publication}/{paymentMode}")
    public ResponseEntity<?> getGatewayInfo(@PathVariable String territoryId, @PathVariable String publication, @PathVariable String paymentMode) {
        boolean isActive=true;
        if(!isProd){
            isActive=false;
            territoryId=null;
        }

        PaymentGateway paymentGateway = paymentGatewayDetails.getPaymentGatewayDetails(territoryId, publication, paymentMode, isActive);
        if (paymentGateway != null) {
            return new ResponseEntity<>(paymentGateway, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
