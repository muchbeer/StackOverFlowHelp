package raum.muchbeer.data.repository.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import raum.muchbeer.data.db.MovieDao;
import raum.muchbeer.data.db.RoomDb;

public class LocalDataSourceFactory extends DataSource.Factory {
    private RoomDb mDb;
    private static final String TAG = LocalDataSourceFactory.class.getSimpleName();
    private LocalDataSourcePageKey moviesPageKeyedDataSource;



    public LocalDataSourceFactory(MovieDao dao) {

        moviesPageKeyedDataSource = new LocalDataSourcePageKey(dao);
    }

    @NonNull
    @Override
    public DataSource create() {
        return moviesPageKeyedDataSource;
    }

}
