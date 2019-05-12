package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface CastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cast cast);

    @Query("SELECT * FROM `cast` WHERE id LIKE :id")
    Single<Cast> getCastById(long id);
}
