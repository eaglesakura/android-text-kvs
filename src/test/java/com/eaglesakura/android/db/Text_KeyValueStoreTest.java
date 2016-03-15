package com.eaglesakura.android.db;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class Text_KeyValueStoreTest extends UnitTestCase {

    int mDbNum = 0;

    public synchronized File newDbFile() {
        return getContext().getDatabasePath(String.format("%d%d", mDbNum++, System.currentTimeMillis()));
    }

    @Test
    public void Key_Valueで値が取得できる() throws Exception {
        TextKeyValueStore db = new TextKeyValueStore(getContext(), newDbFile(), TextKeyValueStore.TABLE_NAME_DEFAULT);
        try {
            db.open(DBOpenType.Write);

            db.insert("SampleKey", "this is value");
        } finally {
            db.close();
        }

        try {
            db.open(DBOpenType.Write);

            assertNotNull(db.list());
            assertEquals(db.list().size(), 1);
            assertEquals(db.get("SampleKey").value, "this is value");
        } finally {
            db.close();
        }
    }

    @Test
    public void LikeでKeyを検索できる() throws Exception {
        TextKeyValueStore db = new TextKeyValueStore(getContext(), newDbFile(), TextKeyValueStore.TABLE_NAME_DEFAULT);
        try {
            db.open(DBOpenType.Write);

            db.insert("SampleKey", "this is value");
            db.insert("Sample", "this is value");
            db.insert("KeyDummy", "this is value");
        } finally {
            db.close();
        }

        try {
            db.open(DBOpenType.Write);

            assertNotNull(db.likeKey("Key"));
            assertEquals(db.likeKey("Key").size(), 2);
        } finally {
            db.close();
        }
    }

    @Test
    public void 存在しないKeyはnullが返却される() throws Exception {
        TextKeyValueStore db = new TextKeyValueStore(getContext(), newDbFile(), TextKeyValueStore.TABLE_NAME_DEFAULT);
        try {
            db.open(DBOpenType.Write);

            db.insert("SampleKey", "this is value");
        } finally {
            db.close();
        }

        try {
            db.open(DBOpenType.Write);

            assertNull(db.get("DeadKey"));
        } finally {
            db.close();
        }
    }
}
