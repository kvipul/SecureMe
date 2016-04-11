package cs654.secureme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


public class SendSMSAccident extends ActionBarActivity {

    CountDownTimer countDownTimer;
    TextView showMessage;
    Button cancelSendSms;
    int flag = 0;

    SharedPreferences sharedPreferences;
    String phoneNo;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_smsaccident);
        showMessage = (TextView) findViewById(R.id.showMsg);
        cancelSendSms = (Button) findViewById(R.id.cancelSms);

        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (flag == 1) {
                    showMessage.setText("sending cancelled!");
                } else {
                    showMessage.setText("seconds remaining: " + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {
                if (flag == 1) {
                    showMessage.setText("sending cancelled!");
                } else {
                    showMessage.setText("done!");
                    final SendSms snd = new SendSms(SendSMSAccident.this);
                    sharedPreferences = getSharedPreferences("details", Context.MODE_PRIVATE);
                    phoneNo = sharedPreferences.getString("helpMobile", "");
                    GPS gps = new GPS(SendSMSAccident.this);
                    if (gps.canGetLocation()) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }

                    Geocoder geocoder = new Geocoder(SendSMSAccident.this, Locale.ENGLISH);
                    String addr="";
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null) {
                            Address fetchedAddress = addresses.get(0);
                            StringBuilder strAddress = new StringBuilder();
                            for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                                strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                            }

                            addr=strAddress.toString();

                        } else {
                            addr="";

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    DecimalFormat df = new DecimalFormat("#.######");
                    String message = "i'm in trouble, help me at " + addr + "(Lat= " + df.format(latitude) + " and Long= " + df.format(longitude) +")";
                    Toast.makeText(SendSMSAccident.this, message, Toast.LENGTH_LONG).show();

                    snd.sendSMSMessage(message, phoneNo);

                }

            }
        }.start();
        System.out.println("hi this is sunil");

        cancelSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_smsaccident, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finish();
//    }
//
//    @Override
//    protected void onPause(){
//        finish();
//        super.onPause();
//
//    }
}
