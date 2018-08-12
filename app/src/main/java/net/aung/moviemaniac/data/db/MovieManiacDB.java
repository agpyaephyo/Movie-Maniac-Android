package net.aung.moviemaniac.data.db;

/**
 * Created by aung on 7/30/17.
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import net.aung.moviemaniac.data.db.dao.MovieDao;
import net.aung.moviemaniac.data.vos.CollectionVO;
import net.aung.moviemaniac.data.vos.MovieVO;

@Database(entities = {MovieVO.class, CollectionVO.class}, version = 1)
public abstract class MovieManiacDB extends RoomDatabase {

    private static final String DB_NAME = "MovieManiac.DB";

    private static MovieManiacDB objInstance;

    public abstract MovieDao movieDao();

    public static MovieManiacDB getMovieManiacDatabase(Context context) {
        if (objInstance == null) {
            objInstance =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), MovieManiacDB.class)
                            .allowMainThreadQueries() //Remove this after testing. Access to DB should always be from background thread.
                            .build();
        }
        return objInstance;
    }

    public static void destoryInstance() {
        objInstance = null;
    }
}
