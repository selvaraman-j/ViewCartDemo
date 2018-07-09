package com.selva.demo.viewcart.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/5/2018
 */

public final class NetworkUtils {
    /**
     * Check whether the internet connection is available or not
     *
     * @return true if device is connected to internet otherwise false
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (null != connectivityManager) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return null != activeNetworkInfo && activeNetworkInfo.isConnected();
    }
}
