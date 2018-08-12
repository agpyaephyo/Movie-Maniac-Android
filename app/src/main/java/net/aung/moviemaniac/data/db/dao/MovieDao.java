package net.aung.moviemaniac.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.aung.moviemaniac.data.vos.MovieVO;

import java.util.List;

/**
 * Created by aung on 7/30/17.
 */
@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertMovies(MovieVO... attractions);

    @Query("SELECT * FROM movie")
    LiveData<List<MovieVO>> getAllMovies();
}
