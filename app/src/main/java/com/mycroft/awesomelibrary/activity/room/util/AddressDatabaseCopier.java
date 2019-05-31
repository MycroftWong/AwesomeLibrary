package com.mycroft.awesomelibrary.activity.room.util;

import android.content.Context;

import androidx.room.Room;

import com.blankj.utilcode.util.FileIOUtils;
import com.mycroft.awesomelibrary.activity.room.dao.AddressDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 地址数据库复制工具类
 *
 * @author mycroft
 */
public class AddressDatabaseCopier {

    private static final String DATABASE_NAME = "address.db";

    private static final String DATABASE_FILE = "databases/" + DATABASE_NAME;

    private AddressDatabase addressDatabase;
    private static Context appContext;

    private static class Holder {
        private static final AddressDatabaseCopier INSTANCE = new AddressDatabaseCopier();
    }

    public static AddressDatabaseCopier getInstance(Context context) {
        appContext = context;
        return Holder.INSTANCE;
    }

    private AddressDatabaseCopier() {
        // 将assets的数据库文件，复制到app私有数据库文件夹里面
        copyAttachedDatabase(appContext);

        // 获取地址文件库持有类
        addressDatabase = Room.databaseBuilder(appContext,
                AddressDatabase.class, DATABASE_NAME)
                // 注意这里是升级数据库，保证复制之后生效
                .addMigrations(AddressDatabase.MIGRATION_1_2)
                .build();
    }

    public AddressDatabase getRoomDatabase() {
        return addressDatabase;
    }

    private void copyAttachedDatabase(Context context) {
        final File dbPath = context.getDatabasePath(AddressDatabaseCopier.DATABASE_NAME);

        // If the database already exists, return
        if (dbPath.exists()) {
            return;
        }

        // Make sure we have a path to the file
        dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = context.getAssets().open(DATABASE_FILE);
            FileIOUtils.writeFileFromIS(dbPath, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
