package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class ExternalPartnerResponse<T> extends BaseResponse{

    private String id;
    private String name;
    @JsonProperty("employee_code")
    private String employeeCode;
    private String mobile;
    private String type;
    @JsonProperty("is_active")
    private boolean isActive;

    public void getPartnerResponse(ExternalPartner parther){
        this.setMobile(parther.getMobile());
        this.setActive(parther.getIsActive());
        this.setName(parther.getName());
        this.setType(parther.getType());
        this.setId(parther.getId());
        this.setEmployeeCode(parther.getEmployeeCode());
        this.setSuccess(true);
        this.setMessage("OK");
    }

}
