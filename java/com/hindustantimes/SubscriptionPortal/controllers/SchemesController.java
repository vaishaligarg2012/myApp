package com.hindustantimes.SubscriptionPortal.controllers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hindustantimes.SubscriptionPortal.Repository.AccountRepository;
import com.hindustantimes.SubscriptionPortal.Repository.TerritoryRepository;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.hindustantimes.SubscriptionPortal.Repository.PublicationSchemeRepository;
import com.hindustantimes.SubscriptionPortal.Repository.RenewalMappingRepository;
import com.hindustantimes.SubscriptionPortal.Services.PublicationSchemeService;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082"})
@RequestMapping(value = "apis/subscription/v1/home/schemes")
public class SchemesController {

    @Autowired
    PublicationSchemeRepository schemerepo;

    @Autowired
    RenewalMappingRepository renewalrepo;

    @Autowired
    TerritoryRepository territoryrepo;

    @Autowired
    AccountRepository accountRepo;

    @Value("${CUSTOMER_BOOKING}")
    String channel;

    @GetMapping("/get-allowed-schemes")
    @ResponseBody
    public ResponseEntity<?> getAllowedSchemes(@RequestParam(name = "sales_org", required = false) String salesOrg,
                                               @RequestParam(name = "scheme", required = false) String schemeCode,
                                               @RequestParam(name = "accountId", required = false) String accountId) throws JSONException, JsonProcessingException {
        List<String> territorySet = new ArrayList<>();
        List<PublicationScheme> schemes;
        List<PublicationScheme> salesOrgSchemes;
        List<PublicationScheme> territorySchemes;
        List<PublicationScheme> allSchemes;
        List<PublicationScheme> finalComboSchemes;
        List<PublicationScheme> finalCurrentSchemes;


        Account account = accountRepo.findById(accountId).get();
        Territory customerMainCentre = account.getMainCentre();
        if (customerMainCentre != null) {
            territorySet.add(customerMainCentre.getId());
            if (customerMainCentre.getZone() != null)
                territorySet.add(customerMainCentre.getZone().getId());
            if (customerMainCentre.getRegion() != null)
                territorySet.add(customerMainCentre.getRegion().getId());
            if (customerMainCentre.getCityUpc() != null)
                territorySet.add(customerMainCentre.getCityUpc().getId());
        }
        if (salesOrg == null) {
            return new ResponseEntity<>("Please provide SalesOrg", HttpStatus.BAD_REQUEST);
        }

        if (schemeCode == null) {
            return new ResponseEntity<>("Please provide valid scheme", HttpStatus.BAD_REQUEST);
        }
        else {
            PublicationScheme currentScheme = schemerepo.findByCode(schemeCode);
            System.out.println(currentScheme);
            if (currentScheme == null)
                return new ResponseEntity<>(
                        String.format("No valid scheme found for code %s", schemeCode),
                        HttpStatus.BAD_REQUEST);
            System.out.println(currentScheme.getName());
            List<RenewalMapping> renewal_schemes = renewalrepo.findByOldSchemeId(currentScheme.getId());
            schemes = renewal_schemes.stream().
                    map(scheme -> scheme.getRenewalScheme()).
                    filter(scheme -> scheme.getIsActive() && scheme.getChannel().contains(channel) && scheme.getBookingType().contains("Renewal")).collect(Collectors.toList());
            salesOrgSchemes = schemes.stream().filter(scheme -> scheme.getTerritory().isEmpty()).collect(Collectors.toList());
            if (territorySet.size() > 0) {
                territorySchemes = schemes.stream().filter(scheme -> scheme.getTerritory() != null && scheme.getTerritory().stream().allMatch(t -> territorySet.contains(t.getId()))).collect(Collectors.toList());
                allSchemes = Stream.concat(salesOrgSchemes.stream(), territorySchemes.stream()).collect(Collectors.toList());
            } else {
                allSchemes = salesOrgSchemes;
            }

            finalCurrentSchemes = allSchemes.stream().distinct().filter(scheme -> scheme.getPublications().equals(currentScheme.getPublications())).collect(Collectors.toList());
            if (currentScheme.getIsActive() && currentScheme.getChannel().contains(channel) && currentScheme.getBookingType().contains("Renewal"))
                finalCurrentSchemes.add(currentScheme);
            finalComboSchemes = allSchemes.stream().distinct().filter(scheme -> !scheme.getPublications().equals(currentScheme.getPublications())).collect(Collectors.toList());
        }
        SchemeResponse baseResponse = new SchemeResponse();
        Map<String, Object> schemesMap = new HashMap();
        List<Object> response = new ArrayList<Object>();
        schemesMap.put("comboSchemes", finalComboSchemes);
        schemesMap.put("currentSchemes", finalCurrentSchemes);
        schemesMap.put("success", true);
        response.add(schemesMap);
        baseResponse.setSchemes(response);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


}
 
