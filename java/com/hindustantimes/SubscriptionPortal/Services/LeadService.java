package com.hindustantimes.SubscriptionPortal.Services;

import com.hindustantimes.SubscriptionPortal.Repository.*;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hindustantimes.SubscriptionPortal.models.OrderRequest;

import java.util.Optional;

@Service
public class LeadService {

    @Autowired
    PublicationSchemeRepository schemerepo;

    @Autowired
    TerritoryRepository territoryrepo;

    @Autowired
    LocalityRepository localityRepo;

    @Autowired
    SocietyRepository societyRepo;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    LeadRepository leadRepository;

    public BaseResponse createLead(OrderRequest orderRequest) {
        System.out.println("Working " + orderRequest.isAddressFilled());
        BaseResponse<?> response = new BaseResponse<>();
        switch (orderRequest.getSource().toLowerCase()) {
            case "digital":
                if (orderRequest.getDigitalOrderId() == null || orderRequest.getCustomerUniqueId() == null || orderRequest.getDigitalOrderId().isEmpty() || orderRequest.getCustomerUniqueId().isEmpty()) {
                    response.setMessage("Provide digital order id and customer id");
                    response.setSuccess(false);
                    return response;
                }
        }
        if (orderRequest.isAddressFilled()) {
            if (addressValidationsFailed(response, orderRequest.getFlatNo(), orderRequest.getFloor(), orderRequest.getLocality(),
                    orderRequest.getLocalityNonMaster(), orderRequest.getSociety(), orderRequest.getSocietyNonMaster(),
                    orderRequest.getBlockOrStreet(), orderRequest.getCity(), orderRequest.getState(), orderRequest.getPinCode()))
                return response;
        }
        PublicationScheme currentScheme = schemerepo.findByCode(orderRequest.getScheme());
        if (currentScheme == null) {
            System.out.println("No valid Scheme found");
            response.setMessage("No valid Scheme found");
            response.setSuccess(false);
            return response;
        }
        orderRequest.setScheme(currentScheme.getId());
        Integer amount = currentScheme.getPrice();
        orderRequest.setAmount(amount);
        Territory location = territoryrepo.findOneByTerritoryCode(orderRequest.getSalesOrg());
        if (location != null) {
            orderRequest.setSalesOrg(location.getId());
        } else {
            response.setMessage("Provide a valid sales org");
            response.setSuccess(false);
            return response;
        }
        Lead lead_instance = leadRepository.findByDigitalOrderIdAndCustomerUniqueId(orderRequest.getDigitalOrderId(), orderRequest.getCustomerUniqueId());
        if (lead_instance != null) {
            response.setMessage("Duplicate order request received");
            response.setSuccess(false);
        } else {
            response.setMessage("Request accepted for new order");
            response.setSuccess(true);
        }
        return response;
    }

    public BaseResponse<?> updateAddress(UpdateOrderRequest updateOrderRequest) {
        BaseResponse<?> response = new BaseResponse<>();
        String flatNo = updateOrderRequest.getFlatNo();
        String floor = updateOrderRequest.getFloor();
        String locality = updateOrderRequest.getLocality();
        String otherLocality = updateOrderRequest.getLocalityNonMaster();
        String society = updateOrderRequest.getSociety();
        String otherSociety = updateOrderRequest.getSocietyNonMaster();
        String blockOrStreet = updateOrderRequest.getBlockOrStreet();
        String city = updateOrderRequest.getCity();
        String state = updateOrderRequest.getState();
        String pinCode = updateOrderRequest.getPinCode();
        Optional<Lead> l = leadRepository.findById(updateOrderRequest.getLeadId());
        Lead lead_instance = l.orElse(null);
        if (lead_instance != null) {
            if (lead_instance.getOpportunity() != null) {
                Opportunity opportunity = lead_instance.getOpportunity();
                if (opportunity.getLocality() != null || opportunity.getOtherLocality() != null) {
                    response.setSuccess(false);
                    response.setMessage("Address already filled");
                    return response;
                }
                if (addressValidationsFailed(response, flatNo, floor, locality, otherLocality, society, otherSociety, blockOrStreet, city, state, pinCode))
                    return response;
                opportunity.setFlatNo(flatNo);
                opportunity.setFloor(floor);
                opportunity.setBlockOrStreet(blockOrStreet);
                if (locality != null)
                    opportunity.setLocality(locality);
                if (otherLocality != null) {
                    opportunity.setOtherLocality(otherLocality);
                    opportunity.setCity(city);
                    opportunity.setState(state);
                    opportunity.setPinCode(pinCode);
                }
                if (society != null)
                    opportunity.setSociety(society);
                if (otherSociety != null)
                    opportunity.setOtherSociety(otherSociety);
                opportunityRepository.save(opportunity);
            } else {
                if (lead_instance.getLocality() != null || lead_instance.getOtherLocality() != null) {
                    response.setSuccess(false);
                    response.setMessage("Address already filled");
                    return response;
                }
                if (addressValidationsFailed(response, flatNo, floor, locality, otherLocality, society, otherSociety, blockOrStreet, city, state, pinCode))
                    return response;
                lead_instance.setFlatNo(flatNo);
                lead_instance.setFloor(floor);
                if (locality != null)
                    lead_instance.setLocality(locality);
                if (otherLocality != null) {
                    lead_instance.setOtherLocality(otherLocality);
                    lead_instance.setCity(city);
                    lead_instance.setState(state);
                    lead_instance.setPinCode(pinCode);
                }
                if (society != null)
                    lead_instance.setSociety(society);
                if (otherSociety != null)
                    lead_instance.setOtherSociety(otherSociety);
                leadRepository.save(lead_instance);
            }
            response.setMessage("Request accepted for address update");
            response.setSuccess(true);
        } else {
            response.setMessage("No valid order found");
            response.setSuccess(false);
        }
        return response;
    }

    private boolean addressValidationsFailed(BaseResponse<?> response, String flatNo, String floor, String locality, String otherLocality,
                                             String society, String otherSociety, String blockOrStreet, String city, String state, String pinCode) {
        if (flatNo == null || blockOrStreet == null ||
                city == null || state == null || pinCode == null ||
                flatNo.trim().isEmpty() || blockOrStreet.trim().isEmpty()
                || city.trim().isEmpty() || state.trim().isEmpty() || pinCode.trim().isEmpty()) {
            System.out.println(" mandatory fields are missing ");
            response.setMessage("mandatory address fields are missing");
            response.setSuccess(false);
            return true;
        }
        if (flatNo.length() > 10) {
            response.setMessage("length of flat no cannot be greater than 10");
            response.setSuccess(false);
            return true;
        }
        if (floor != null && floor.length() > 5) {
            response.setMessage("length of floor cannot be greater than 5");
            response.setSuccess(false);
            return true;
        }
        if (blockOrStreet.length() > 50) {
            response.setMessage("length of block/street cannot be greater than 50");
            response.setSuccess(false);
            return true;
        }
        if(locality == null || locality.trim().isEmpty()){
            if(otherLocality == null || otherLocality.trim().isEmpty()){
                response.setMessage("provide one out of locality or locality non master");
                response.setSuccess(false);
                return true;
            }
        }
        else if(otherLocality == null || otherLocality.trim().isEmpty()){
            if(locality == null || locality.trim().isEmpty()){
                response.setMessage("provide one out of locality or locality non master");
                response.setSuccess(false);
                return true;
            }
            else{
                if(localityRepo.findById(locality).isEmpty()){
                    response.setMessage("provide a valid locality");
                    response.setSuccess(false);
                    return true;
                }

            }
        }
        else{
            response.setMessage("Either provide locality or locality non master, not both");
            response.setSuccess(false);
            return true;
        }
        if(society == null || society.trim().isEmpty()){
            if(otherSociety == null || otherSociety.trim().isEmpty()){
                response.setMessage("provide one out of society or society non master");
                response.setSuccess(false);
                return true;
            }
        }
        else if(otherSociety == null || otherSociety.trim().isEmpty()){
            if(society == null || society.trim().isEmpty()){
                response.setMessage("provide one out of society or society non master");
                response.setSuccess(false);
                return true;
            }
            else{
                if(societyRepo.findById(society).isEmpty()){
                    response.setMessage("provide a valid society");
                    response.setSuccess(false);
                    return true;
                }

            }
        }
        else{
            response.setMessage("Either provide society or society non master, not both");
            response.setSuccess(false);
            return true;
        }
        return false;
    }

}
