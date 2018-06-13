package xyz.vivekc.popularmovies.repository.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MoviesApiService getApiService() {
        return getRetrofitInstance().create(MoviesApiService.class);
    }

    public static String getImageUrl(String posterPath) {
        return "http://image.tmdb.org/t/p/w185//"+posterPath;
    }

    public static String getBackdropImageUrl(String backdropPath) {
        return "http://image.tmdb.org/t/p/w342//"+backdropPath;
    }
}
