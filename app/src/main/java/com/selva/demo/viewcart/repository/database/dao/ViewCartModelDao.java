package com.selva.demo.viewcart.repository.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.selva.demo.viewcart.repository.database.entity.ViewCartModelEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/9/2018
 */

@Dao
public interface ViewCartModelDao {
    @Query("select * from ViewCartModelEntity")
    List<ViewCartModelEntity> getViewCartList();

    @Insert(onConflict = REPLACE)
    void addCart(List<ViewCartModelEntity> liveDataList);

    @Update(onConflict = REPLACE)
    void updateCartItem(ViewCartModelEntity viewCartModelEntity);

    @Delete
    void deleteCartItem(ViewCartModelEntity viewCartModelEntity);
}
