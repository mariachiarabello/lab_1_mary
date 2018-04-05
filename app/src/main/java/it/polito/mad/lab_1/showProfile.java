package it.polito.mad.lab_1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class showProfile extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.editButton) {
            Intent intent = new Intent(this, editProfile.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView emailView = (TextView) findViewById(R.id.emailView);
        TextView bioView = (TextView) findViewById(R.id.bioView);
        TextView birthdayView = (TextView) findViewById(R.id.birthdayView);
        TextView locationView = (TextView) findViewById(R.id.locationView);
        TextView phoneView = (TextView) findViewById(R.id.phoneView);

        String jsonString = "";
        JSONObject jsonObject;

        try {
            InputStream nameInputStream = getApplicationContext().openFileInput("profileName.bin");
            if (nameInputStream!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(nameInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String buildString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((buildString = bufferedReader.readLine())!=null){
                    stringBuilder.append(buildString);
                }
                nameInputStream.close();
                jsonString = stringBuilder.toString();
            }
        }catch (Exception e){
            Log.e("Input error", e.getMessage());
        }

        try {
            jsonObject = new JSONObject(jsonString);
            if (jsonObject.get("name").toString().equals("")){
                nameView.setText(getString(R.string.default_name));
            }else {
                nameView.setText(jsonObject.get("name").toString());
            }
            if (jsonObject.get("birthday").toString().equals("")){
                birthdayView.setText(getString(R.string.default_birthday));
            }else {
                birthdayView.setText(jsonObject.get("birthday").toString());
            }
            if (jsonObject.get("phone").toString().equals("")){
                phoneView.setText(getString(R.string.default_phone_number));
            }else {
                phoneView.setText(jsonObject.get("phone").toString());
            }
            if (jsonObject.get("email").toString().equals("")){
                emailView.setText(getString(R.string.default_email));
            } else {
                emailView.setText(jsonObject.get("email").toString());
            }
            if (jsonObject.get("location").toString().equals("")){
                locationView.setText(getString(R.string.default_location));
            }else {
                locationView.setText(jsonObject.get("location").toString());
            }
            if (jsonObject.get("bio").toString().equals("")) {
                bioView.setText(getString(R.string.default_bio));
            } else {
                bioView.setText(jsonObject.get("bio").toString());
            }

        }catch (Exception e){
            Log.e("Error while parsing JSON", e.getMessage());
        }
    }
}
