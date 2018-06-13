package xyz.vivekc.popularmovies.repository.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.vivekc.popularmovies.model.ListResponse;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;

public interface MoviesApiService {

    @GET("popular")
    Call<ListResponse> getPopularMovies(@Query("page") int pageNumber, @Query("api_key") String apiKey);

    @GET("top_rated")
    Call<ListResponse> getTopRatedMovies(@Query("page") int pageNumber, @Query("api_key") String apiKey);

    @GET("{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int movieId,@Query("append_to_response") String appendToResponse ,@Query("api_key") String apiKey);
}
