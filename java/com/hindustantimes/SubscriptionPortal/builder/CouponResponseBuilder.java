package com.hindustantimes.SubscriptionPortal.builder;

import com.hindustantimes.SubscriptionPortal.Repository.CouponRepository;
import com.hindustantimes.SubscriptionPortal.Repository.CouponScheduleRepository;
import com.hindustantimes.SubscriptionPortal.Repository.CouponValidityRepository;
import com.hindustantimes.SubscriptionPortal.Repository.OrderRepository;
import com.hindustantimes.SubscriptionPortal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class CouponResponseBuilder {
    @Autowired
    OrderRepository orderRepo;

    @Autowired
    CouponRepository couponRepo;

    @Autowired
    CouponScheduleRepository couponScheduleRepo;

    @Autowired
    CouponValidityRepository couponValidityRepo;



    public Map<String, Object> fetchAllCoupons(String accountId,String publication) {
        Map<String,Object> response = new HashMap<>();
        String sapPublication = Constants.HEROKU_SAP_PUBLICATIONS.get(publication);
        String couponPreference = new String("M");
        List<Order> orders = orderRepo.findByAccount_IdAndCouponPreferenceOrderByCouponStartDate(accountId,couponPreference).stream().
                filter(order -> order.getScheme().getSapPublication().equals(sapPublication) &&
                        Constants.WEEKDAYS.contains(order.getScheme().getDays())).collect(Collectors.toList());
        for(Order order : orders){
            List<Coupons> coupons = couponRepo.findBySapOrderNumberAndCouponNumber(order.getSapOrderNumber(),order.getCouponNumber());
            Set<Integer> redeemedMonths = new HashSet<>();
            for(Coupons coupon:coupons){
                redeemedMonths.add(coupon.getMonthOfRedemption());
            }
            String couponNumber = order.getCouponNumber();
            String sapOrderNumber = order.getSapOrderNumber();
            PublicationScheme scheme = order.getScheme();
            String preference = order.getCouponPreference();
            String locationId = order.getLocation().getId();
            Integer validDays = getValidDays(locationId);
            Calendar validFrom = getValidFrom(validDays);
            Calendar validTo = getValidTo();
            Calendar couponStartDate = order.getCouponStartDate();
            Calendar couponEndDate = order.getCouponEndDate();
            List<CouponSchedule> schedule = couponScheduleRepo.findByScheme_id(scheme.getId());
            int months = schedule.size();
            Integer rate;
            if(order.getType().equals("Secondary")){
                rate = scheme.getParentScheme().getPrice()/(months*2);
            }
            else{
                if(scheme.getSchemeCombo().equals("C")){
                    rate = scheme.getPrice()/(months*2) ;
                }
                else{
                    rate = scheme.getPrice()/months;
                }
            }
            List<CouponLeaf> redeemed = findRedeemedCoupons(coupons,sapOrderNumber,publication,rate,couponNumber,locationId,preference);
            List<CouponLeaf> couponsToRedeem = findCouponsToRedeem(redeemedMonths,sapOrderNumber,publication,rate,couponNumber,
                    locationId,schedule,validFrom,validTo,couponStartDate,validDays,preference);
            List<CouponLeaf> redeemInFuture = findCouponsToRedeemInFuture(redeemedMonths,sapOrderNumber,publication,rate,couponNumber,
                    locationId,schedule,validFrom,validTo,couponStartDate,validDays,preference);
            List<CouponLeaf> expiredCoupons = expiredCoupons(redeemedMonths,sapOrderNumber,publication,rate,couponNumber,
                    locationId,schedule,validFrom,validTo,couponStartDate,validDays,preference);
            updateResponse(response,"redeemed",redeemed);
            updateResponse(response,"redeem_now",couponsToRedeem);
            updateResponse(response,"redeem_in_future",redeemInFuture);
            updateResponse(response,"expired",expiredCoupons);

        }

        return response;
    }

    private void updateResponse(Map<String, Object> response, String key, List<CouponLeaf> toAdd) {
            if(response.containsKey(key)){
                List<CouponLeaf> coupons = (List<CouponLeaf>)response.get(key);
                response.put(key,coupons.addAll(toAdd));
            }
            else{
                response.put(key,toAdd);
            }
    }

    private Calendar getValidFrom(Integer days) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE,-days);
        return today;
    }

    private Integer getValidDays(String locationId) {
        Integer days = couponValidityRepo.findByLocation_id(locationId).getDays();
        return days;
    }

    private Calendar getValidTo() {

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE,30);
        return today;


    }



    private List<CouponLeaf> findRedeemedCoupons(List<Coupons> coupons, String sapOrderNumber, String sapPublication,
                                                 Integer rate,  String couponNumber,String locationId,String preference) {
        List<CouponLeaf> redeemedCoupons = new ArrayList<>();
        Integer validDays = getValidDays(locationId);
        for(Coupons coupon: coupons){
            if(Constants.REDEEMED_STATUS.contains(coupon.getStatus())){
                String type = Constants.COUPON_TYPES_HEROKU.get(coupon.getType());
                CouponLeaf leaf = createCouponLeaf(coupon.getMonthOfRedemption(),sapOrderNumber,couponNumber,type,sapPublication,
                        coupon.getExpiryDate(),locationId,rate,validDays,preference);
                redeemedCoupons.add(leaf);
            }
        }
        return redeemedCoupons;
    }

    private List<CouponLeaf> findCouponsToRedeem(Set<Integer> redeemedMonths, String sapOrderNumber, String sapPublication,
                                                 Integer rate,String couponNumber,String locationId,List<CouponSchedule> schedule,
                                                 Calendar validFrom,Calendar validTo,Calendar couponStartDate,Integer validDays,String preference) {
        List<CouponLeaf> couponsToRedeem = new ArrayList<>();
        for(CouponSchedule s: schedule){
            if(!redeemedMonths.contains(s.getMonth())){
                String type = s.getCouponType();
                Calendar expiryDate = Calendar.getInstance();
                expiryDate.setTime(couponStartDate.getTime());
                expiryDate.add(Calendar.HOUR,6);

                expiryDate.add(Calendar.MONTH,s.getMonth());
                if(expiryDate.compareTo(validFrom) >= 0 && expiryDate.compareTo(validTo) <= 0) {
                    CouponLeaf leaf = createCouponLeaf(s.getMonth(),sapOrderNumber,couponNumber,type,sapPublication,expiryDate,
                            locationId,rate,validDays,preference);
                    couponsToRedeem.add(leaf);
                }
            }
        }
        return couponsToRedeem;
    }

    private CouponLeaf createCouponLeaf(Integer month, String sapOrderNumber, String couponNumber, String type, String sapPublication,
                                        Calendar expiryDate, String locationId, Integer rate, Integer validDays,String preference) {
        CouponLeaf leaf = new CouponLeaf();
        String couponNo = couponNumber.substring(0,2) + Constants.COUPON_TYPES.get(type) + couponNumber.substring(3);
        leaf.setRate(rate);
        leaf.setMonthOfRedemption(month);
        leaf.setCouponPreference(preference);
        leaf.setSapOrderNumber(sapOrderNumber);
        leaf.setType(type);
        leaf.setCouponNumber(couponNo);
        leaf.setPublication(sapPublication);
        leaf.setExpiryDate(expiryDate);
        leaf.setLocation(locationId);
        leaf.setValidUpto(getValidUpto(expiryDate,validDays));
        return leaf;
    }

    private List<CouponLeaf> expiredCoupons(Set<Integer> redeemedMonths, String sapOrderNumber, String sapPublication,
                                                 Integer rate,String couponNumber,String locationId,List<CouponSchedule> schedule,
                                                 Calendar validFrom,Calendar validTo,Calendar couponStartDate,Integer validDays,String preference) {
        List<CouponLeaf> coupons = new ArrayList<>();
        for(CouponSchedule s: schedule){
            if(!redeemedMonths.contains(s.getMonth())){
                String type = s.getCouponType();
                Calendar expiryDate = Calendar.getInstance();
                expiryDate.setTime(couponStartDate.getTime());
                expiryDate.add(Calendar.MONTH,s.getMonth());
                expiryDate.add(Calendar.HOUR,6);
                if(expiryDate.compareTo(validFrom) <= 0) {
                    CouponLeaf leaf = createCouponLeaf(s.getMonth(),sapOrderNumber,couponNumber,type,sapPublication,expiryDate,
                            locationId,rate,validDays,preference);
                    coupons.add(leaf);
                }
            }
        }
        return coupons;
    }

    private List<CouponLeaf> findCouponsToRedeemInFuture(Set<Integer> redeemedMonths, String sapOrderNumber, String sapPublication,
                                                         Integer rate,String couponNumber,String locationId,List<CouponSchedule> schedule,
                                                         Calendar validFrom,Calendar validTo,Calendar couponStartDate,Integer validDays,String preference) {
        List<CouponLeaf> couponsToRedeemInFuture = new ArrayList<>();
        for(CouponSchedule s: schedule){
            if(!redeemedMonths.contains(s.getMonth())){
                String type = s.getCouponType();
                Calendar expiryDate = Calendar.getInstance();
                expiryDate.setTime(couponStartDate.getTime());
                expiryDate.add(Calendar.MONTH,s.getMonth());
                expiryDate.add(Calendar.HOUR,6);
                if(expiryDate.compareTo(validTo) >= 0) {
                    CouponLeaf leaf = createCouponLeaf(s.getMonth(),sapOrderNumber,couponNumber,type,sapPublication,expiryDate,
                            locationId,rate,validDays,preference);
                    couponsToRedeemInFuture.add(leaf);
                }
            }
        }
        return couponsToRedeemInFuture;
    }

    private Calendar getValidUpto(Calendar expiryDate, Integer days) {
        if(days > 3000)
            return null;
        Calendar validUpto = Calendar.getInstance();
        validUpto.setTime(expiryDate.getTime());
        validUpto.add(Calendar.DATE,days);
        return validUpto;
    }

}
