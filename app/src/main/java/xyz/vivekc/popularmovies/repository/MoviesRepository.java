package xyz.vivekc.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.vivekc.popularmovies.model.ListResponse;
import xyz.vivekc.popularmovies.model.moviedetails.MovieDetails;
import xyz.vivekc.popularmovies.repository.api.ApiResponse;
import xyz.vivekc.popularmovies.repository.api.ApiService;

@SuppressWarnings("NullableProblems")
public class MoviesRepository {

    /**
     * Method would get the page number and API key for getting the popularmovies.
     * This current implementation does not have pagination support. In future, when pagination
     * is supported, the pageNumber param would be used
     * @param pageNumber page number to be fetched
     * @param apiKey TMDB api key
     * @return LiveData instance which would emit various stages of ApiResponse for each state of api call
     */
    public LiveData<ApiResponse<ListResponse>> getPopularMovies(int pageNumber, String apiKey) {
        final MutableLiveData<ApiResponse<ListResponse>> liveData = new MutableLiveData<>();

        //initially send the loading state with no data and error message
        ApiResponse<ListResponse> apiResponse = new ApiResponse<>();
        apiResponse.currentState = ApiResponse.State.LOADING;
        liveData.setValue(apiResponse);

        ApiService.getApiService().getPopularMovies(pageNumber, apiKey).enqueue(new CustomCallback<>(liveData));
        return liveData;
    }

    /**
     * Method would get the page number and API key for getting the top rated movies.
     * This current implementation does not have pagination support. In future, when pagination
     * is supported, the pageNumber param would be used
     * @param pageNumber page number to be fetched
     * @param apiKey TMDB api key
     * @return LiveData instance which would emit various stages of ApiResponse for each state of api call
     */
    public LiveData<ApiResponse<ListResponse>> getTopRatedMovies(int pageNumber, String apiKey) {
        final MutableLiveData<ApiResponse<ListResponse>> liveData = new MutableLiveData<>();

        //initially send the loading state with no data and error message
        ApiResponse<ListResponse> apiResponse = new ApiResponse<>();
        apiResponse.currentState = ApiResponse.State.LOADING;
        liveData.setValue(apiResponse);

        ApiService.getApiService().getTopRatedMovies(pageNumber, apiKey).enqueue(new CustomCallback<>(liveData));
        return liveData;
    }

    /**
     * Method would get the movieId and API key for getting the movie details.
     * This method fetches the movie details along with `casts` as "append_to_response" param.
     * This appendToResponse would save us much needed bandwidth as many API calls to TMDB server could be combined to one.
     * @param movieId ID of the movie for which details are required
     * @param apiKey TMDB api key
     * @return LiveData instance which would emit various stages of ApiResponse for each state of api call
     */
    public LiveData<ApiResponse<MovieDetails>> getMovieDetails(int movieId, String apiKey) {
        final MutableLiveData<ApiResponse<MovieDetails>> liveData = new MutableLiveData<>();

        //initially send the loading state with no data and error message
        ApiResponse<MovieDetails> apiResponse = new ApiResponse<>();
        apiResponse.currentState = ApiResponse.State.LOADING;
        liveData.setValue(apiResponse);

        ApiService.getApiService().getMovieDetails(movieId, "casts", apiKey).enqueue(new CustomCallback<>(liveData));
        return liveData;
    }


    /**
     * A CustomCallback implementation which takes care of the API calls.
     * This class would be responsible for getting the API call response and other states
     * and propogate it to the UI using LiveData.
     *
     * @param <T> Model type which would be returned from the API call
     */
    class CustomCallback<T> implements Callback<T> {

        MutableLiveData<ApiResponse<T>> liveData;

        CustomCallback(MutableLiveData<ApiResponse<T>> liveData) {
            this.liveData = liveData;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            Log.d("ApiRequest", call.request().url().toString());
            if (response.body() != null) {
                Log.d("ApiResponse", response.body().toString());
            }
            ApiResponse<T> apiResponse = new ApiResponse<>();
            if (response.isSuccessful()) {
                //API successful!
                apiResponse.data = response.body();
                apiResponse.currentState = ApiResponse.State.SUCCESS;
                liveData.setValue(apiResponse);
            } else {
                //API hit success but TMDB throwed some error.
                apiResponse.data = response.body();
                apiResponse.currentState = ApiResponse.State.ERROR;
                liveData.setValue(apiResponse);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            //API response failed!
            Log.d("ApiRequest", call.request().url().toString());
            t.printStackTrace();
            ApiResponse<T> apiResponse = new ApiResponse<>();
            apiResponse.throwable = t;
            apiResponse.currentState = ApiResponse.State.FAILURE;
            liveData.setValue(apiResponse);
        }
    }
}
