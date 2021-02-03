package com.hindustantimes.SubscriptionPortal.builder;

import java.util.*;
import java.util.stream.Collectors;

import com.hindustantimes.SubscriptionPortal.Repository.OpportunityRepository;
import com.hindustantimes.SubscriptionPortal.Repository.OrderRepository;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class LandingPageResponseBuilder {

    @Autowired
    OrderResponseBuilder orderResponseBuilder;

    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    OrderRepository orderRepo;
    public LandingPageResponse buildLandingPageResponse(Account accountInfo){
        String fullAddress="";
        String localityName="";
        String societyName ="";
        String locationCode="";
        String societyId ="";
        String localityId="";
        AccountAddressResponse addressDetails =new AccountAddressResponse();
        addressDetails.setFloor(accountInfo.getFloor());
        addressDetails.setFlatNum(accountInfo.getFlatNum());
        addressDetails.setApartment(accountInfo.getAppartment());
        addressDetails.setBlockOrStreet(accountInfo.getBlockOrStreet());
        addressDetails.setCity(accountInfo.getCity());
        addressDetails.setState(accountInfo.getState());
        if(accountInfo.getSociety() != null){
            societyName = accountInfo.getSociety().getName();
            societyId = accountInfo.getSociety().getId();
        }
        addressDetails.setSector(accountInfo.getSector());
        if(accountInfo.getLocality() != null){
            localityName = accountInfo.getLocality().getName();
            locationCode =accountInfo.getLocation().getTerritoryCode();
            localityId = accountInfo.getLocality().getId();
        }
        addressDetails.setPincode(accountInfo.getPinCode());
        addressDetails.setSocietyName(societyName);
        addressDetails.setLocalityName(localityName);
        addressDetails.setSocietyId(societyId);
        addressDetails.setLocalityId(localityId);
        fullAddress = accountInfo.getFlatNum()+" " +accountInfo.getFloor()+" "+
                accountInfo.getAppartment()+", "+accountInfo.getBlockOrStreet()+", "+accountInfo.getSector() +" , "+
                accountInfo.getArea()+", "+societyName+", "+ localityName+" , "+accountInfo.getCity()+" , "+accountInfo.getState()+" , "+
                accountInfo.getLocality().getPincode();
        fullAddress = fullAddress.replace("null", "");
        LandingPageResponse landingPageResponse = new LandingPageResponse();
        landingPageResponse.setId(accountInfo.getId());
        landingPageResponse.setAddress(fullAddress.replaceAll("(,)*$", ""));
        landingPageResponse.setAddressDetails(addressDetails);
        landingPageResponse.setLocationCode(locationCode);
        String nameAndLastname=accountInfo.getFirstName() +" "+ accountInfo.getLastName();
        nameAndLastname = nameAndLastname.replace("null", "");
        landingPageResponse.setCustomerName(nameAndLastname);
        landingPageResponse.setMobile(accountInfo.getMobileNumber());
        ExternalPartner vendor = accountInfo.getVendor();
        if(vendor != null){
            landingPageResponse.setVendorId(vendor.getId());
            landingPageResponse.setVendorCode(vendor.getEmployeeCode());
            landingPageResponse.setVendorName(vendor.getName());
            landingPageResponse.setVendorMobile(vendor.getMobile());
        }
        landingPageResponse.setEmail(accountInfo.getEmail());
        List<Object> orders = fetchAllOrders(accountInfo);
        landingPageResponse.setOrders(orders);
        return landingPageResponse;
    }


    public List<Object> fetchAllOrders(Account account){
        Map<String, Object> orders = new HashMap<>();
        if (account != null) {
            List<Order> orderList = orderRepo.findByAccount(account).stream().filter(order -> order.getType().equalsIgnoreCase("Primary")).sorted(Comparator.comparing(Order::getStartDate).reversed()).collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            List<OrderResponse> orderFilter = orderResponseBuilder.buildOrderResponse(orderList);
            orderFilter.forEach(order -> {
                try {

                    String serOrder = new ObjectMapper().writeValueAsString(order);
                    Object pojoObj = mapper.readValue(serOrder, Object.class);
                    mapper.writeValueAsString(pojoObj);
                    boolean isRepeatedPublicationOrder = false;
                    for (String p : order.getScheme().getPublications().split(";")) {
                        if (orders.keySet().stream().filter(k -> k.contains(p)).count() > 0) {
                            isRepeatedPublicationOrder = true;
                            break;
                        }
                    }
                    if (!isRepeatedPublicationOrder)
                        orders.put(order.getScheme().getPublications(), pojoObj);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        return Arrays.asList(orders.values().toArray());

    }
}