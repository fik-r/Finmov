package com.fizus.mobiledev.finmov.data.network.responses;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface CreditsResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GetCreditsResponse creditsResponse);

    @Query("SELECT * FROM credits_response WHERE id LIKE :id")
    Single<GetCreditsResponse> getCreditsResponse(long id);
}
