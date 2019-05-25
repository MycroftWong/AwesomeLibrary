package com.mycroft.awesomelibrary.activity.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "street")
public class Street {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "county_id")
    public int county_id;
    @ColumnInfo(name = "name")
    public String name;

    public Street() {
    }

    @Ignore
    public Street(int id, int county_id, String name) {
        this.id = id;
        this.county_id = county_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounty_id() {
        return county_id;
    }

    public void setCounty_id(int county_id) {
        this.county_id = county_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}