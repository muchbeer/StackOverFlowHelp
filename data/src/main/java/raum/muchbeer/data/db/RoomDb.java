package raum.muchbeer.data.db;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import raum.muchbeer.data.model.Movie;
import raum.muchbeer.data.repository.paging.LocalDataSourceFactory;

@Database(entities = {Movie.class},version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    //paging declaration
    private static final Object sLock = new Object();
    private LiveData<PagedList<Movie>> moviesPaged;


    static final String DATABASE_NAME = "movie_db";
    private static final int NUMBERS_OF_THREADS = 4;
    private static RoomDb INSTANCE;

    public abstract MovieDao movieDao();




    public static synchronized RoomDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE)
                .setPageSize(Integer.MAX_VALUE).build();

        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

        LocalDataSourceFactory dataSourceFactory = new LocalDataSourceFactory(movieDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.setFetchExecutor(executor).build();

    }

    public LiveData<PagedList<Movie>> getMoviesPagingLocal() {
        return moviesPaged;
    }
}
