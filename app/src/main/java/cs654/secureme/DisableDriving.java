package cs654.secureme;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

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
    int tempVar=0;

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
                        System.out.println("ll1" + tempVar);

                        Thread.sleep(5000);

                        lat2 = gps.getLatitude();
                        long2 = gps.getLongitude();
                        System.out.println("ll1" + tempVar);
                        ++tempVar;



                        GetDistanceBetweenTwoPointsGPS getDistanceBetweenTwoPointsGPS = new GetDistanceBetweenTwoPointsGPS();
                        double xy=getDistanceBetweenTwoPointsGPS.getDist(lat1,long1,lat2,long2);

                        double speed = xy * 1;
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


    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

}
