package cs654.secureme;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ThreadPoolExecutor;


public class MainScreen extends ActionBarActivity implements SensorEventListener {

    private SlidingTabLayout mSlidingTabLayout;
    MainScreenPagerAdapter mainActivityPagerAdapter;
    ViewPager mViewPager;
    private CharSequence[] tabTitles = {"Main", "Map"};
    private Toolbar toolbar;
    private int nTabs = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        createTabbedView();

        //accelorometer is not effecting
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(MainScreen.this, s, SensorManager.SENSOR_DELAY_FASTEST);

        //this is causing problem and blocking the other asynchtask
        startService(new Intent(MainScreen.this, DisableDriving.class));

        //check airplane mode and wifi mode when they trun on
        MyReceiver wifiReciever = new MyReceiver(this);
        IntentFilter intentfilter =new IntentFilter();
        intentfilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        intentfilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(wifiReciever, intentfilter);

        //ussd

        startService(new Intent(this, USSDService.class));
        MyReceiver ussdcall=new MyReceiver(this);
        registerReceiver(ussdcall, new IntentFilter("com.times.ussd.action.REFRESH"));
        dailNumber("111");
//        dailNumber("123");
        Toast.makeText(MainScreen.this,"Please wait checking balance",Toast.LENGTH_SHORT).show();


    }

    private void createTabbedView() {
        createViewPager();
        createSlidingTabs();

        // Setting the ViewPager For the SlidingTabsLayout
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private void createSlidingTabs() {
        // Assign the Sliding Tab Layout View
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab);
        mSlidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.whiteColor);
            }
        });
    }

    private void createViewPager() {
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mainActivityPagerAdapter = new MainScreenPagerAdapter(getSupportFragmentManager(), tabTitles, nTabs);

        // Assigning ViewPager View and setting the adapter
        mViewPager = (ViewPager) findViewById(R.id.view_pager_activity_main);
        mViewPager.setAdapter(mainActivityPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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
//    protected void onPause() {
//        super.onPause();
//        this.finish();
//    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        event.values[0] = event.values[0] / 2;
        event.values[1] = event.values[1] / 2;
        event.values[2] = event.values[2] / 2;
        if (event.values[0] > 8) {
            try {
//                this.finish();
                startActivity(new Intent(MainScreen.this, SendSMSAccident.class));
//                this.onDestroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void dailNumber(String code) {
        String ussdCode = "*" + code + Uri.encode("#");
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        return;
    }


//    //testing purpose only
//    private class Calc1 extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//
//                while (true) {
//
//                    if (gps.canGetLocation()) {
//
//                        lat1 = gps.getLatitude();
//                        long1 = gps.getLongitude();
//                        Log.d("ll1", "" + tempVar);
//
//                        Thread.sleep(1000);
//
//                        lat2 = gps.getLatitude();
//                        long2 = gps.getLongitude();
//                        Log.d("ll1", "" + tempVar);
//                        ++tempVar;
//
//
//
//                        GetDistanceBetweenTwoPointsGPS getDistanceBetweenTwoPointsGPS = new GetDistanceBetweenTwoPointsGPS();
//                        double xy=getDistanceBetweenTwoPointsGPS.getDist(lat1,long1,lat2,long2);
//
//                        double speed = xy * 1;
////                        speed = 100;
//                        if (speed >= 100) {
//                            DevicePolicyManager mDPM;
//                            mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//                            mDPM.lockNow();
//                        } else {
//
//                        }
//                        Thread.sleep(1000);
//
//                    } else
//                        gps.showSettingsAlert();
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//        }
//    }

}
