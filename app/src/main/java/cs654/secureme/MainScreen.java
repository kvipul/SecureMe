package cs654.secureme;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainScreen extends ActionBarActivity implements SensorEventListener {

    private SlidingTabLayout mSlidingTabLayout;
    MainScreenPagerAdapter mainActivityPagerAdapter;
    ViewPager mViewPager;
    private CharSequence[] tabTitles = {"Main", "Track", "Map"};
    private Toolbar toolbar;
    private int nTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        createTabbedView();

        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(MainScreen.this, s, SensorManager.SENSOR_DELAY_FASTEST);

        startService(new Intent(MainScreen.this,DisableDriving.class));

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


}
