package xyz.vivekc.popularmovies.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MovieItem implements Serializable {

    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("video")
    @Expose
    public Boolean video;
    @SerializedName("vote_average")
    @Expose
    public Float voteAverage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("popularity")
    @Expose
    public Float popularity;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;

    @Override
    public String toString() {
        return "MovieItem{" +
                "voteCount=" + voteCount +
                ", id=" + id +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", genreIds=" + genreIds +
                ", backdropPath='" + backdropPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}