package com.mycroft.awesomelibrary.activity.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mycroft.awesomelibrary.activity.room.entity.City;
import com.mycroft.awesomelibrary.activity.room.entity.County;
import com.mycroft.awesomelibrary.activity.room.entity.Province;
import com.mycroft.awesomelibrary.activity.room.entity.Street;

import java.util.List;

@Dao
public interface AddressDao {

    /**
     * @return
     */
    @Query("SELECT * FROM province")
    List<Province> loadAllProvinces();

    @Query("SELECT * FROM city")
    List<City> loadAllCities();

    @Query("SELECT * FROM county")
    List<County> loadAllCounties();

    @Query("SELECT * FROM street")
    List<Street> loadAllStreets();

    @Query("SELECT * FROM city where id=:id")
    List<City> loadCitiesById(int id);

    @Query("SELECT * FROM county where id=:id")
    List<County> loadCountiesById(int id);

    @Query("SELECT * FROM street where id=:id")
    List<Street> loadStreetsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertProvinces(List<Province> provinces);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertCities(List<City> cities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertCounties(List<County> counties);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertStreets(List<Street> streets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertProvince(Province province);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCounty(County county);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertStreet(Street street);

}
