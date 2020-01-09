package raum.muchbeer.data.repository.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.subjects.ReplaySubject;
import raum.muchbeer.data.api.MovieDataService;
import raum.muchbeer.data.api.RetroInstance;
import raum.muchbeer.data.model.Movie;
import raum.muchbeer.data.model.MovieDbResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static raum.muchbeer.data.utility.Constant.API_KEY;
import static raum.muchbeer.data.utility.Constant.LANGUAGE;

public class RemoteDataSourcePageKey extends PageKeyedDataSource<Long, Movie> {

    private static final String LOG_TAG = RemoteDataSourcePageKey.class.getSimpleName();;
    private final MovieDataService moviesService;
    private  MutableLiveData<String> mError=new MutableLiveData<>();
    private final ReplaySubject<Movie> moviesObservable;

    RemoteDataSourcePageKey() {
        moviesService = RetroInstance.getService();

        mError = new MutableLiveData<>();
        moviesObservable = ReplaySubject.create();
    }

    public MutableLiveData<String> getErrorStream() {
        return mError;
    }


    public ReplaySubject<Movie> getMoviesReplay() {
        return moviesObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        //this is paging
        Call<MovieDbResponse> call =  moviesService.getPopularMoviesWithPaging(API_KEY,
                LANGUAGE,
                1);
        //this below is without paging
        // Call<MovieDbResponse> call = movieDataService.getPopularMovies(application.getApplicationContext().getString(R.string.apiKey));
        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                MovieDbResponse movieDbResponse = response.body();
                if (movieDbResponse != null && movieDbResponse.getMovies() != null) {

                    // moview = (ArrayList<Movie>) movieDbResponse.getMovies();
                    Log.d(LOG_TAG, "All the movies listed as : "+ movieDbResponse.getMovies());

                    // mDataApi.setValue(moview);

                    callback.onResult(movieDbResponse.getMovies(), (long)1, (long)2);

                    movieDbResponse.getMovies().forEach(moviesObservable::onNext);
                } else {
                    Log.e("API CALL FAILURE: ", response.toString());
                     mError.postValue(response.toString());

                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable response) {
                String errorMessage;
                if (response.getMessage() == null) {
                    errorMessage = "unknown error";
                    mError.postValue(errorMessage);
                    Log.d(LOG_TAG, "The error catched is as follows : "+ errorMessage);


                } else {
                    errorMessage = response.getMessage();
                    mError.postValue(errorMessage);
                    Log.d(LOG_TAG, "The error catched is as follows : "+ errorMessage);

                }



                callback.onResult(new ArrayList<>(), (long) 1, (long) 1);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

        Log.i(LOG_TAG, "Loading page " + params.key );

        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(String.valueOf(params.key)));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        //this is paging
        Call<MovieDbResponse> call =  moviesService.getPopularMoviesWithPaging(API_KEY,
                LANGUAGE,
                1);
        //this below is without paging
        // Call<MovieDbResponse> call = movieDataService.getPopularMovies(application.getApplicationContext().getString(R.string.apiKey));
        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                MovieDbResponse movieDbResponse = response.body();
                if (movieDbResponse != null && movieDbResponse.getMovies() != null) {

                    // moview = (ArrayList<Movie>) movieDbResponse.getMovies();
                    Log.d(LOG_TAG, "All the movies listed as : "+ movieDbResponse.getMovies());

                    // mDataApi.setValue(moview);

                    callback.onResult(movieDbResponse.getMovies(), params.key+1);
                      movieDbResponse.getMovies().forEach(moviesObservable::onNext);
                } else {
                    Log.e("API CALL FAILURE: ", response.toString());
                       mError.postValue(response.toString());
                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable response) {
                String errorMessage;
                if (response.getMessage() == null) {
                    errorMessage = "unknown error";
                    mError.postValue(errorMessage);
                    Log.d(LOG_TAG, "The error catched is as follows : "+ errorMessage);
                     } else {
                    errorMessage = response.getMessage();
                    mError.postValue(errorMessage);
                    Log.d(LOG_TAG, "The error catched is as follows : "+ errorMessage);
                 }

                callback.onResult(new ArrayList<>(), (long)(page.get()));
                //  mError.postValue(response.toString());

            }
        });
    }
}
