package com.example.qrapp.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Connectivity {
    /*
     * HACKISH: These constants aren't yet available in my API level (7), but I need to handle these cases if they come up, on newer versions
     */
    private static final int NETWORK_TYPE_EHRPD = 14; // Level 11
    private static final int NETWORK_TYPE_EVDO_B = 12; // Level 9
    private static final int NETWORK_TYPE_HSPAP = 15; // Level 13
    private static final int NETWORK_TYPE_IDEN = 11; // Level 8
    private static final int NETWORK_TYPE_LTE = 13; // Level 11
    private static  Context mContext ;

    public Connectivity(Context context) {
        mContext = context;
    }


    // Check internet connection
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    // Check internet connection Faster and check type (wifi || mobile data)
    public static boolean isConnectedFast(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && Connectivity.isConnectionFast(info.getType(), info.getSubtype()));
    }


    private static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
//            System.out.println("CONNECTED VIA WIFI");
            Toast.makeText(mContext, "CONNECTED VIA WIFI", Toast.LENGTH_LONG).show();
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            Toast.makeText(mContext, "CONNECTED VIA MOBILE", Toast.LENGTH_LONG).show();
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    Toast.makeText(mContext, "50-100 kbps", Toast.LENGTH_LONG);
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    Toast.makeText(mContext, "~ 14-64 kbps", Toast.LENGTH_LONG);
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    Toast.makeText(mContext, "~ 50-100 kbps", Toast.LENGTH_LONG);
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                // NOT AVAILABLE YET IN API LEVEL 7
                case Connectivity.NETWORK_TYPE_EHRPD:
                    return true; // ~ 1-2 Mbps
                case Connectivity.NETWORK_TYPE_EVDO_B:
                    return true; // ~ 5 Mbps
                case Connectivity.NETWORK_TYPE_HSPAP:
                    return true; // ~ 10-20 Mbps
                case Connectivity.NETWORK_TYPE_IDEN:
                    return false; // ~25 kbps
                case Connectivity.NETWORK_TYPE_LTE:
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

}