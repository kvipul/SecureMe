package cs654.secureme;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ViPul Sublaniya on 20-03-2016.
 */
public class SendSms {
    Context context;
    SharedPreferences sharedPreferences;
    String phoneNo;
    double latitude,longitude;

    public SendSms(Context context) {
        this.context = context;
    }

    protected void sendSMSMessage(String message, String phoneNo) {
        Log.i("Send SMS", "");
//        String phoneNo = "8560057004";
//        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);
//        phoneNo = sharedPreferences.getString("helpMobile", "");
//        GPS gps = new GPS(context);
//        if (gps.canGetLocation()) {
//            latitude = gps.getLatitude();
//            longitude = gps.getLongitude();
//        }
//
//        String message = "i'm in trouble, help me at GPS location. Lat= "+latitude+" and Long= "+longitude;


        try {

            String SENT = "SMS_SENT";


            PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);

            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    int resultCode = getResultCode();
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            Toast.makeText(context, "SMS sent", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context, "Generic failure", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(context, "No service", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "Null PDU", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "Radio off", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            SmsManager smsMgr = SmsManager.getDefault();
            smsMgr.sendTextMessage(phoneNo, null, message, sentPI, null);

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "!\n" + "Failed to send SMS", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}

