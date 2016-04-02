package cs654.secureme;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StartScreen extends ActionBarActivity {

    EditText fname, lname, yourMobile, helpMobile;
    Button submit, devAdmin;
    SharedPreferences sharedPreferences;
    Intent intent;
    DevicePolicyManager mDPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        yourMobile = (EditText) findViewById(R.id.yourMobile);
        helpMobile = (EditText) findViewById(R.id.helpMobile);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                String yMobile = yourMobile.getText().toString();
                String hMobile = helpMobile.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fname", firstName);
                editor.putString("lname", lastName);
                editor.putString("yourMobile", yMobile);
                editor.putString("helpMobile", hMobile);
                editor.commit();

                DevicePolicyManager mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);;
                ComponentName mAdminName = new ComponentName(StartScreen.this, DeviceAdminM.class);
                if (!mDPM.isAdminActive(mAdminName)) {
                    devAdmin.setVisibility(View.VISIBLE);
                } else {
                    intent = new Intent(StartScreen.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                }


            }
        });


        devAdmin = (Button) findViewById(R.id.devAd);
        devAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enable device admin
                ComponentName mAdminName = new ComponentName(StartScreen.this, DeviceAdminM.class);

                Intent intent1 = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent1.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                intent1.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Please restart the app after activating this feature");
                startActivityForResult(intent1, 0);
                //
            }
        });

        sharedPreferences = getSharedPreferences("fname", Context.MODE_PRIVATE);
        checkDetails();


    }

    public void checkDetails() {
        if (sharedPreferences.contains("fname") && sharedPreferences.contains("lname") && sharedPreferences.contains("yourMobile") && sharedPreferences.contains("helpMobile")) {
            intent = new Intent(this, MainScreen.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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

    public class DeviceAdminM extends DeviceAdminReceiver {

        void showToast(Context context, String msg) {
//            String status = context.getString(R.string.admin_receiver_status, msg);
//            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
//            showToast(context, context.getString(R.string.admin_receiver_status_enabled));
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            return context.getString(R.string.hello_blank_fragment);
        }

        @Override
        public void onDisabled(Context context, Intent intent) {
            showToast(context, context.getString(R.string.hello_blank_fragment));
        }

        @Override
        public void onPasswordChanged(Context context, Intent intent) {
            showToast(context, context.getString(R.string.hello_blank_fragment));
        }

    }

}
