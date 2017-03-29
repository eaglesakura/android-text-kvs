package com.eaglesakura.android.db;

import com.eaglesakura.lambda.ResultAction1;
import com.eaglesakura.util.CollectionUtil;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class TextKeyValueStoreTest extends UnitTestCase {

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
            assertEquals(db.getOrNull("SampleKey"), "this is value");
            assertEquals(db.getOrNull("__no_key__"), null);
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
            db.open(DBOpenType.Read);

            assertNotNull(db.likeKey("Key"));
            assertEquals(db.likeKey("Key").size(), 2);
        } finally {
            db.close();
        }
    }

    @Test
    public void 同じValueを持つKeyを検索できる() throws Throwable {
        TextKeyValueStore db = new TextKeyValueStore(getContext(), newDbFile(), TextKeyValueStore.TABLE_NAME_DEFAULT);
        try {
            db.open(DBOpenType.Write);

            db.insert("Key1", "this is value");
            db.insert("Key2", "this is value");
            db.insert("Key3", "this is value");
        } finally {
            db.close();
        }

        try {
            db.open(DBOpenType.Read);

            List<String> keys = CollectionUtil.asOtherList(db.listValues("this is value"), new ResultAction1<TextKeyValueStore.Data, String>() {
                @Override
                public String action(TextKeyValueStore.Data it) throws Throwable {
                    return it.key;
                }
            });
            assertEquals(keys.size(), 3);
            assertTrue(keys.contains("Key1"));
            assertTrue(keys.contains("Key2"));
            assertTrue(keys.contains("Key3"));
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
