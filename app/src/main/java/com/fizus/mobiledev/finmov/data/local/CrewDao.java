package com.fizus.mobiledev.finmov.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface CrewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Crew crew);

    @Query("SELECT * FROM crew WHERE id LIKE :id")
    Single<Crew> getCrewById(long id);
}
