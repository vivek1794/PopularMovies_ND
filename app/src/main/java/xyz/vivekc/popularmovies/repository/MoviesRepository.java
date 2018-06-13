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

    public LiveData<ApiResponse<ListResponse>> getPopularMovies(int pageNumber, String apiKey) {
        final MutableLiveData<ApiResponse<ListResponse>> liveData = new MutableLiveData<>();

        //initially send the loading state with no data and error message
        ApiResponse<ListResponse> apiResponse = new ApiResponse<>();
        apiResponse.currentState = ApiResponse.State.LOADING;
        liveData.setValue(apiResponse);

        ApiService.getApiService().getPopularMovies(pageNumber, apiKey).enqueue(new CustomCallback<>(liveData));
        return liveData;
    }

    public LiveData<ApiResponse<ListResponse>> getTopRatedMovies(int pageNumber, String apiKey) {
        final MutableLiveData<ApiResponse<ListResponse>> liveData = new MutableLiveData<>();

        //initially send the loading state with no data and error message
        ApiResponse<ListResponse> apiResponse = new ApiResponse<>();
        apiResponse.currentState = ApiResponse.State.LOADING;
        liveData.setValue(apiResponse);

        ApiService.getApiService().getTopRatedMovies(pageNumber, apiKey).enqueue(new CustomCallback<>(liveData));
        return liveData;
    }

    public LiveData<ApiResponse<MovieDetails>> getMovieDetails(int movieId,String apiKey) {
        final MutableLiveData<ApiResponse<MovieDetails>> liveData = new MutableLiveData<>();

        //initially send the loading state with no data and error message
        ApiResponse<MovieDetails> apiResponse = new ApiResponse<>();
        apiResponse.currentState = ApiResponse.State.LOADING;
        liveData.setValue(apiResponse);

        ApiService.getApiService().getMovieDetails(movieId,"casts" ,apiKey).enqueue(new CustomCallback<>(liveData));
        return liveData;
    }


    class CustomCallback<T> implements Callback<T> {

        MutableLiveData<ApiResponse<T>> liveData;

        CustomCallback(MutableLiveData<ApiResponse<T>> liveData) {
            this.liveData = liveData;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            Log.d("ApiRequest",call.request().url().toString());
            if (response.body() != null) {
                Log.d("ApiResponse",response.body().toString());
            }
            ApiResponse<T> apiResponse = new ApiResponse<>();
            if (response.isSuccessful()) {
                apiResponse.data = response.body();
                apiResponse.currentState = ApiResponse.State.SUCCESS;
                liveData.setValue(apiResponse);
            } else {
                apiResponse.data = response.body();
                apiResponse.currentState = ApiResponse.State.ERROR;
                liveData.setValue(apiResponse);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d("ApiRequest",call.request().url().toString());
            t.printStackTrace();
            ApiResponse<T> apiResponse = new ApiResponse<>();
            apiResponse.throwable = t;
            apiResponse.currentState = ApiResponse.State.FAILURE;
            liveData.setValue(apiResponse);
        }
    }
}
