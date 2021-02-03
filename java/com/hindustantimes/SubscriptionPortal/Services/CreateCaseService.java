package com.hindustantimes.SubscriptionPortal.Services;

import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CreateCaseService {

    @Autowired
    CirculationAuthService circulationAuthService;

    @Value("${CIRCULATION_URL}")
    String circulationUrl;


    public void updateRequest(CaseCreateRequest request,HttpHeaders token){
        String apiUrl = circulationUrl + "/circulation/case/api/get-case-picklist/";
        RestTemplate restTemplate = new RestTemplate();
        request.setSource("ssp");
        HttpEntity<CaseCreateRequest> entity = new HttpEntity<>(token);
        ResponseEntity<?> res= restTemplate.exchange(apiUrl, HttpMethod.GET,entity, CasePicklistResponse.class);
        CasePicklistResponse response = (CasePicklistResponse) res.getBody();
        List<CaseType> caseTypes = response.getCaseTypes();
        for(CaseType type : caseTypes){
            List<CaseSubtype> caseSubTypes = type.getSubtype();
            for(CaseSubtype subtype:caseSubTypes){
                if(subtype.getName().equals(request.getDescription())){
                    request.setIdentifier(subtype.getIdentifier());
                    request.setSubtype(subtype.getId());
                    request.setType(type.getId());
                    return;
                }
            }
        }
        return ;


    }

    public ResponseEntity<?> createCase(CaseCreateRequest request){

        HttpHeaders token = circulationAuthService.generateTokenWithHeader();
        updateRequest(request,token);
        String apiUrl = circulationUrl + "/circulation/case/api/case-get-or-create/";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CaseCreateRequest> entity = new HttpEntity<>(request,token);
        ResponseEntity<?> response= restTemplate.postForEntity(apiUrl, entity, CirculationBaseResponse.class);
        System.out.println(response);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

    }
}
