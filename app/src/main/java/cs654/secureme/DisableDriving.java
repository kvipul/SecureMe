package cs654.secureme;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sunil on 5/4/16.
 */
public class DisableDriving extends Service {
    double lat1, lat2;
    double long1, long2;
    double tempLat, tempLong;
    GPS gps;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Calc c = new Calc();
        c.execute();
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    //temp to check something
    private class Calc extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {

                while (true) {

                    gps = new GPS(getApplicationContext());
                    if (gps.canGetLocation()) {

                        lat1 = gps.getLatitude();
                        long1 = gps.getLongitude();
                        Thread.sleep(1000);

                        lat2 = gps.getLatitude();
                        long2 = gps.getLongitude();

                        tempLat = toRad(lat2 - lat1);
                        tempLong = toRad(long2 - long1);

                        double x = Math.sin(tempLat / 2) * Math.sin(tempLat / 2) + Math.sin(tempLong / 2) * Math.sin(tempLong / 2) * Math.cos(toRad(lat1)) * Math.cos(toRad(lat2));
                        double y = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
                        double xy = 6371 * y;
                        System.out.println("ll1" + xy + "xy");
                        double speed = xy * 1000;
//                        speed = 100;
                        if (speed >= 100) {
                            DevicePolicyManager mDPM;
                            mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                            mDPM.lockNow();
                        } else {

                        }
                        Thread.sleep(2000);

                    } else
                        gps.showSettingsAlert();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }


    public double toRad(double x) {
        return x * Math.PI / 180;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

}
