package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Data
@Table(name = "coupons_coupon_validity")
public class CouponValidity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "days")
    private Integer days;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="location_id")
    private Territory location;

}
