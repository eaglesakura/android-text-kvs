package com.eaglesakura.android.db;

import com.eaglesakura.util.StringUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * 簡易設定用のプロパティを保持するためのクラス
 */
public class Properties {
    PropertyStore mPropertyStore;

    public PropertyStore getPropertyStore() {
        return mPropertyStore;
    }

    public void setPropertyStore(PropertyStore propertyStore) {
        mPropertyStore = propertyStore;
    }

    public String getStringProperty(String key) {
        return mPropertyStore.getStringProperty(key);
    }

    public void setProperty(String key, Object value) {
        mPropertyStore.setProperty(key, value);
    }

    public void clear() {
        mPropertyStore.clear();
    }

    public void commit() {
        mPropertyStore.commit();
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(getStringProperty(key));
    }

    public long getLongProperty(String key) {
        return Long.parseLong(getStringProperty(key));
    }

    public Date getDateProperty(String key) {
        return new Date(getLongProperty(key));
    }

    public float getFloatProperty(String key) {
        return Float.parseFloat(getStringProperty(key));
    }

    public boolean getBooleanProperty(String key) {
        String value = getStringProperty(key);

        // 保存速度を向上するため、0|1判定にも対応する
        if ("0".equals(value)) {
            return false;
        } else if ("1".equals(value)) {
            return true;
        }
        return Boolean.parseBoolean(getStringProperty(key));
    }

    public double getDoubleProperty(String key) {
        return Double.parseDouble(getStringProperty(key));
    }

    /**
     * 画像ファイル形式で保存してあるBitmapを取得する
     */
    @Nullable
    public Bitmap getImageProperty(String key) {
        byte[] imageFile = getByteArrayProperty(key);
        if (imageFile == null) {
            return null;
        }

        try {
            return BitmapFactory.decodeStream(new ByteArrayInputStream(imageFile));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * base64エンコードオブジェクトを取得する
     */
    @Nullable
    public byte[] getByteArrayProperty(String key) {
        try {
            return StringUtil.toByteArray(getStringProperty(key));
        } catch (Exception e) {
            return null;
        }
    }
}
