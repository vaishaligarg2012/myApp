package com.hindustantimes.SubscriptionPortal.models;

import java.util.*;

public final class  Constants {
    public static final List<String> REDEEMED_STATUS =
            Collections.unmodifiableList(
            new ArrayList<String>() {{
        add("S");
        add("C");
        add("R");
        add("E");
        add("D");
    }});
    public static final List<String> WEEKDAYS =
            Collections.unmodifiableList(
                    new ArrayList<String>() {{
                        add("MO-FR");
                        add("MO-SA");
                        add("VA");
                    }});

    public static final Map<String, String> HEROKU_SAP_PUBLICATIONS =
            Collections.unmodifiableMap(
                    new HashMap<>(){{
                        put("HT","HT");
                        put("HH","HH");
                        put("Mint","BP");
                    }}
            );

    public static final Map<String, String> SMS_TEXT =
            Collections.unmodifiableMap(
                    new HashMap<>(){{
                        put("vendorotp","Welcome to HT Media Group. You have initiated redemption of m-coupons." +
                                " Your verification Code is %d. Kindly share the same with the Vendor for further processing.");
                        put("address_change","Welcome to HT Media Group. You have initiated a request for address change." +
                                " Your verification Code is %d.");
                        put("ssp","Welcome to HT Media Group. Your verification Code is %d");
                        put("digital","Welcome to HT Media Group. Your verification Code is %d");

                    }}
            );
    public static final Map<String, String> COUPON_TYPES =
            Collections.unmodifiableMap(
                    new HashMap<>(){{
                        put("Implementation","I");
                        put("Redemption","R");
                        put("Gift","G");
                        put("Lucky Draw","L");
                    }}
            );
    public static final Map<String, String> COUPON_TYPES_HEROKU =
            Collections.unmodifiableMap(
                    new HashMap<>(){{
                        put("I","Implementation");
                        put("R","Redemption");
                        put("G","Gift");
                        put("L","Lucky Draw");
                    }}
            );


}
