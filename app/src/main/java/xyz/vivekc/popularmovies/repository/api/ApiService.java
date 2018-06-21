package xyz.vivekc.popularmovies.repository.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    /**
     * Enum to specify the size of the image to be downloaded
     */
    public enum ImageSize {
        POSTER_SIZE("w185"), AVATAR_SIZE("w92"), BACKDROP_SIZE("w342");

        ImageSize(String imageSize) {
            this.size = imageSize;
        }

        String size;
    }

    /**
     * Method to return a Retrofit instance for the TMDB base url and GSON Converter
     * @return Retrofit instance
     */
    private static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Retruns an instance of MoviesApiService using the Retrofit instance
     * @return MoviesApiService instance to do api calls
     */
    public static MoviesApiService getApiService() {
        return getRetrofitInstance().create(MoviesApiService.class);
    }

    /**
     * Util method to provide the URL given the image path and image size
     * @param imagePath - file name with extension of the image file
     * @param imageSize - ImageSize enum to specify the image width required
     * @return String which represents the full image url
     */
    public static String getImageUrl(String imagePath, ImageSize imageSize) {
        return "http://image.tmdb.org/t/p/"+imageSize.size+"/" + imagePath;
    }
}
