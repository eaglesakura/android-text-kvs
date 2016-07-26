package com.eaglesakura.android.db;

import com.eaglesakura.android.AndroidSupportTestCase;
import com.eaglesakura.android.db.model.PropertySource;
import com.eaglesakura.android.tkvs.BuildConfig;
import com.eaglesakura.json.JSON;

import org.robolectric.annotation.Config;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Config(constants = BuildConfig.class, packageName = BuildConfig.APPLICATION_ID, sdk = 21)
public class UnitTestCase extends AndroidSupportTestCase {

    protected File getPropertyJson() {
        return getTestAsset("properties.json");
    }

    @SuppressLint("NewApi")
    protected PropertySource loadProperties() throws Throwable {
        try (InputStream is = new FileInputStream(getPropertyJson())) {
            return JSON.decode(is, PropertySource.class);
        }
    }
}
