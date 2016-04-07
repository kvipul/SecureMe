package cs654.secureme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ViPul Sublaniya on 20-03-2016.
 */
public class MyReceiver extends BroadcastReceiver {

    Context context;

    public MyReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.times.ussd.action.REFRESH")) {
            String message = intent.getExtras().getString("message");
            //MainActivity.ussdtxt.setText(message);

            int i, j = 4, z = 0;

            for (i = 5; i < message.length(); i++) {
                if (message.charAt(i) == 'e' && message.charAt(i - 1) == 'c' && message.charAt(i - 2) == 'n' && message.charAt(i - 3) == 'a' && message.charAt(i - 4) == 'l') {
                    Log.d("i", Integer.toString(i));
                    for (j = i; j < message.length(); j++) {
                        Log.d("j", Integer.toString(j));

                        if (message.charAt(j) > '1' && message.charAt(j) < '9') {
                            Log.d("j", Integer.toString(j));
                            z = j;
                            break;
                        }
                    }
                    while ((message.charAt(j) >= '1' && message.charAt(j) <= '9') || message.charAt(j) == '.') {
                        j++;
                    }
                    break;
                }
            }
            F1.balance.setText("your balance is : " + message.substring(z - 1, j));
            if (Integer.parseInt(message.substring(z - 1, j)) <= 5) {
                final SendSms snd = new SendSms(context);
//                snd.sendSMSMessage("CREDIT","144");//vodafone
//                MyReceiver ussdcall=new MyReceiver(context);
//                context.registerReceiver(ussdcall, new IntentFilter("com.times.ussd.action.REFRESH"));
                dailNumber("141*10*1");
                Toast.makeText(context, "Please wait checking balance", Toast.LENGTH_SHORT).show();
            }


            System.out.println("balance is sunil" + message.substring(z - 1, j));

        }
        if (intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
            //ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
            if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                //MainActivity.test.setText("wifi connected");
            } else {        // wifi connection was lost
                //MainActivity.test.setText("wifi disconnected");
            }
        } else if (intent.getAction().intern() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            //int count=1;
            boolean isEnabled = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
            if (isEnabled) {
                F1.txt.setText("airoplanemode enabled");
                //MainActivity.test.setText("airoplane mode is forced to off " + (1) + " times");
                Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
                Log.d("isenabled", " 0");

//                try {
//                        Thread.sleep(8000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON,1);
//
//                    if(count==1){
//                        countDownTimer.start();
//                    }
//                    if(countDownTimer!=null){
//                        if(count==3){
//                            Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
//                            countDownTimer.cancel();
//                        }
//                        else{
//                            Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
//                        }
//                    }
//                    else{
//                        Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
//                    }
            } else {
                //MainActivity.test.setText("airoplane mode is disabled");
                F1.txt.setText("airoplanemode disabling");
                Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
            }
        }
    }

    private void dailNumber(String code) {
        String ussdCode = "*" + code + Uri.encode("#");
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }
}
