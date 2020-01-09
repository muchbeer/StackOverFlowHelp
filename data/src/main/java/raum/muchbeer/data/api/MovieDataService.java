package raum.muchbeer.data.api;

import java.util.ArrayList;

import raum.muchbeer.data.model.Movie;
import raum.muchbeer.data.model.MovieDbResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static raum.muchbeer.data.utility.Constant.API_KEY_REQUEST_PARAM;
import static raum.muchbeer.data.utility.Constant.LANGUAGE_REQUEST_PARAM;
import static raum.muchbeer.data.utility.Constant.PAGE_REQUEST_PARAM;

public interface MovieDataService {

    @GET("movie/popular")
    Call<MovieDbResponse> getPopularMovies(@Query("api_key") String apiKey);

    //This one is paging Retrofit
    @GET("movie/popular")
    Call<MovieDbResponse> getPopularMoviesWithPaging(@Query("api_key") String apiKey,
                                                     @Query(LANGUAGE_REQUEST_PARAM) String language,
                                                     @Query("page") long page);

    @GET(".")
    Call<ArrayList<Movie>> getMovies(@Query(API_KEY_REQUEST_PARAM) String apiKey,
                                     @Query(LANGUAGE_REQUEST_PARAM) String language,
                                     @Query(PAGE_REQUEST_PARAM) int page);
}
