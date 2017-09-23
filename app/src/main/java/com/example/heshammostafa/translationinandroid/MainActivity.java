package com.example.heshammostafa.translationinandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.Setting:
                Intent intent = new Intent(this, Setting.class);
                startActivity(intent);
                break;
            case R.id.Arabic:
//                lang("ar");
                changeLanguageSettings(this,new Locale("ar","ar_EG"));


                break;
            case R.id.Engish:
//                lang("en");
                changeLanguageSettings(this,Locale.ENGLISH);

                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public void lang(String languageToLoad)
    {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());

    }

    public void changeLanguageSettings(Context con, Locale language) {
        try {
            //Set language
            Locale locale = language;

            //Getting by reflection the ActivityManagerNative
            Class amnClass = Class.forName("android.app.ActivityManagerNative");
            Object amn = null;
            Configuration config = null;

            // amn = ActivityManagerNative.getDefault();
            Method methodGetDefault = amnClass.getMethod("getDefault");
            methodGetDefault.setAccessible(true);
            amn = methodGetDefault.invoke(amnClass);

            // config = amn.getConfiguration();
            Method methodGetConfiguration = amnClass.getMethod("getConfiguration");
            methodGetConfiguration.setAccessible(true);
            config = (Configuration) methodGetConfiguration.invoke(amn);

            // config.userSetLocale = true;
            Class configClass = config.getClass();
            Field f = configClass.getField("userSetLocale");
            f.setBoolean(config, true);

            // Update language
            config.locale = locale;

            // amn.updateConfiguration(config);
            Method methodUpdateConfiguration = amnClass.getMethod(
                    "updateConfiguration", Configuration.class);
            methodUpdateConfiguration.setAccessible(true);
            methodUpdateConfiguration.invoke(amn, config);

        } catch (Exception e) {

            Log.d("error-->", "" + e.getMessage().toString());
        }
    }





//
//    public void setLocale() {
//        Locale locale = getLocaleFromPref();
//        Locale.setDefault(locale);
//        Configuration config = getBaseContext().getResources().getConfiguration();
//        overwriteConfigurationLocale(config, locale);
//    }
//    private void overwriteConfigurationLocale(Configuration config, Locale locale) {
//        config.locale = locale;
//        getBaseContext().getResources()
//                .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//    }
}

