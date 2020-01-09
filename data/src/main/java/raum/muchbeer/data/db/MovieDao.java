package raum.muchbeer.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import raum.muchbeer.data.model.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("SELECT * FROM movietbl")
    LiveData<List<Movie>> getAllMoviesLive();

    @Query("SELECT * FROM movietbl")
    List<Movie> getAllMovieLocal();

    @Query("SELECT * FROM movietbl LIMIT :limit")
    LiveData<List<Movie>>getMovie(int limit);

    @Query("SELECT * FROM movietbl WHERE title=:title")
    LiveData<Movie>getSearchedMovie(String title);


    @Query("SELECT * FROM movietbl")
    List<Movie> getMoviesPaging();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMoviePaging(Movie movie);


}
