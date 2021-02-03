package com.hindustantimes.SubscriptionPortal.common;

import java.util.concurrent.ThreadLocalRandom;

public final class Utils {

    public static int getOldId(){
        return ThreadLocalRandom.current().nextInt(100000000, 2147483646);
    }
}
