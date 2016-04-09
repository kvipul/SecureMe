package cs654.secureme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sunil on 5/4/16.
 */
public class SendMyTracking extends Service {
    GPS gps;
    double latitude, longitude;
    String yourMobile = "12", helpMobile = "12";
    String url1;
    SharedPreferences sharedPreferences;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getApplicationContext().getSharedPreferences("details", Context.MODE_PRIVATE);
        yourMobile = sharedPreferences.getString("yourMobile", "");
        helpMobile = sharedPreferences.getString("helpMobile", "");
        System.out.println("mobile " + yourMobile);
        System.out.println("sunil123 " + helpMobile);


        gps = new GPS(this);
        try {

            final SendSms snd = new SendSms(SendMyTracking.this);
            sharedPreferences = getSharedPreferences("details", Context.MODE_PRIVATE);
            helpMobile = sharedPreferences.getString("helpMobile", "");
            String message = "I have enable my tracking on your phone. Please open the app and track me";
//            snd.sendSMSMessage(message, helpMobile);

            StoreLocation str = new StoreLocation();
            str.execute(url1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }


    private class StoreLocation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {


            while (true) {
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    System.out.println("sunil sunil " + latitude + " " + longitude);
                    System.out.println("sunil sunil 2" + latitude + " " + longitude);

                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    for (String url1 : urls) {
                        url1 = "http://172.20.176.195/cs654/project/tracking.php/send_track/" + yourMobile + "/" + helpMobile + "/" + latitude + "/" + longitude;


                        try {
                            URL url = new URL(url1);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            sb = new StringBuilder();
                            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(5000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (F2.sendMyTrack == false) {
                    break;
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}
