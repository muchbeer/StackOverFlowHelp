package raum.muchbeer.data.repository.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import raum.muchbeer.data.db.MovieDao;
import raum.muchbeer.data.model.Movie;

public class LocalDataSourcePageKey  extends PageKeyedDataSource<Long, Movie> {

    public static final String TAG = LocalDataSourcePageKey.class.getSimpleName();
    private final MovieDao movieDao;

    public LocalDataSourcePageKey(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
    private final MutableLiveData<String> mError=new MutableLiveData<>();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Movie> movies = movieDao.getMoviesPaging();
        if(movies.size() != 0) {
            callback.onResult(movies, (long)0, (long)1);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }
    public void insertMoviesOnline2Local(Movie movies) {
        try {
            movieDao.insertMoviePaging(movies);
        }catch(Exception e)
        {
            e.printStackTrace();
            mError.postValue(e.getMessage());
        }
    }

    public LiveData<String> getErrorStream() {
        return mError;
    }
}
