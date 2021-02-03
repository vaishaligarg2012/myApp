package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class CasePicklistResponse {
    private List<Object> status;
    @JsonProperty("case_type")
    private List<CaseType> caseTypes;

}
