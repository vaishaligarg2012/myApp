package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Table(name = "coupons_coupons")
public class Coupons {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "coupon_qr_code")
    private String couponQrCode;

    @Column(name = "publication_id")
    private String publication;

    @Column(name = "coupon_no")
    private String couponNumber;

    @Column(name = "month_of_redemption")
    private Integer monthOfRedemption;

    @Column(name = "expiry_date")
    private Calendar expiryDate;

    @Column(name = "sap_order_number")
    private String sapOrderNumber;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "publication_scheme_code")
    private String schemeCode;

    @Column(name = "publication_scheme_name")
    private String schemeName;

    @Column(name = "main_center_code")
    private String mainCenterCode;

    @Column(name = "main_center_name")
    private String mainCenterName;

    @Column(name = "agency_code")
    private String agencyCode;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "status")
    private String status;

    @Column(name = "coupon_preference")
    private String couponPreference;

    @Column(name = "scanned_by_username")
    private String scannedByUsername;

    @Column(name = "coupon_type")
    private String type;

    @Column(name = "scanned_by_userfullname")
    private String scannedByUserFullname;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="publication_scheme_id")
    private PublicationScheme scheme;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="vendor_id")
    private ExternalPartner vendor;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="agency_id")
    private ExternalPartner agency;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="main_center_id")
    private Territory mainCentre;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name="location_id")
    private Territory location;

}
