package com.selva.demo.viewcart.repository.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.selva.demo.viewcart.repository.database.dao.ViewCartModelDao;
import com.selva.demo.viewcart.repository.database.entity.ViewCartModelEntity;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/9/2018
 */
@Database(entities = {ViewCartModelEntity.class}, version = 1)
public abstract class ViewCartDatabase extends RoomDatabase {

    private static final String DB_NAME = ViewCartDatabase.class.getSimpleName();
    private static volatile ViewCartDatabase instance;

    public abstract ViewCartModelDao getViewCartDao();

    /**
     * Create and returns the single instance for the room data base
     *
     * @param context The application context
     * @return The ViewCartDatabase
     */
    public static synchronized ViewCartDatabase getInstance(Context context) {
        if (null == instance) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , ViewCartDatabase.class, DB_NAME).build();
        }
        return instance;
    }
}
