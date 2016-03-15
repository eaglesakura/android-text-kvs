package com.eaglesakura.android.db;

import com.eaglesakura.android.AndroidSupportTestCase;
import com.eaglesakura.android.tkvs.BuildConfig;

import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, packageName = BuildConfig.APPLICATION_ID, sdk = 21)
public class UnitTestCase extends AndroidSupportTestCase {
}
