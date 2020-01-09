package raum.muchbeer.data.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import raum.muchbeer.data.model.Movie;
import raum.muchbeer.data.repository.paging.RemoteDataSourceFactory;

public class MovieNetwork {

    public static final int LOADING_PAGE_SIZE = 20;

    final private static String TAG = MovieNetwork.class.getSimpleName();
    private static final int NUMBERS_OF_THREADS = 4;
    final private LiveData<PagedList<Movie>> moviesPaged;

    private LiveData<String> mLocalError;


    public MovieNetwork(RemoteDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Movie> boundaryCallback){

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE)
                .setPageSize(LOADING_PAGE_SIZE).build();

        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged =       livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

        mLocalError = dataSourceFactory.getErrorMessage();
    }


    public LiveData<PagedList<Movie>> getPagedMoviesByPaging(){
        return moviesPaged;
    }

    public LiveData<String>  getmLocalError() {
        return mLocalError;
    }
}
