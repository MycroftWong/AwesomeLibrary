package com.mycroft.awesomelibrary.activity.room.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mycroft.awesomelibrary.activity.room.entity.City;
import com.mycroft.awesomelibrary.activity.room.entity.County;
import com.mycroft.awesomelibrary.activity.room.entity.Province;
import com.mycroft.awesomelibrary.activity.room.entity.Street;

/**
 * @author mycroft
 */
@Database(entities = {Province.class, City.class, County.class, Street.class}, version = 1)
public abstract class AddressDatabase extends RoomDatabase {

    public abstract AddressDao addressDao();
}
