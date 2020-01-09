package raum.muchbeer.data.repository.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import io.reactivex.subjects.ReplaySubject;
import raum.muchbeer.data.model.Movie;

public class RemoteDataSourceFactory extends DataSource.Factory<Long, Movie> {

    private static final String TAG = RemoteDataSourceFactory.class.getSimpleName();
    private MutableLiveData<RemoteDataSourcePageKey> networkStatus;
    private MutableLiveData<String> mError;
    private RemoteDataSourcePageKey moviesPageKeyedDataSource;


    public RemoteDataSourceFactory() {

        this.networkStatus=new MutableLiveData<>();
        this.mError = new MutableLiveData<>();
        moviesPageKeyedDataSource = new RemoteDataSourcePageKey();

    }


    @NonNull
    @Override
    public DataSource<Long, Movie> create() {
        networkStatus.postValue(moviesPageKeyedDataSource);
        // mError.postValue(moviesPageKeyedDataSource);
        return moviesPageKeyedDataSource;

    }

    public MutableLiveData<RemoteDataSourcePageKey> getNetworkStatus() {
        return networkStatus;
    }

    public MutableLiveData<String> getErrorMessage() {
        return moviesPageKeyedDataSource.getErrorStream();
    }
    public ReplaySubject<Movie> getMoviesPaging() {
        return moviesPageKeyedDataSource.getMoviesReplay();
    }
}
