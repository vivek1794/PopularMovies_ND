package xyz.vivekc.popularmovies.model.moviedetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("budget")
    @Expose
    public Integer budget;
    @SerializedName("genres")
    @Expose
    public List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("popularity")
    @Expose
    public Float popularity;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("revenue")
    @Expose
    public Integer revenue;
    @SerializedName("runtime")
    @Expose
    public Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    public List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("tagline")
    @Expose
    public String tagline;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("video")
    @Expose
    public Boolean video;
    @SerializedName("vote_average")
    @Expose
    public Float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("casts")
    @Expose
    public Casts casts;

    // Not using these for now. Might need it in the future
//    @SerializedName("belongs_to_collection")
//    @Expose
//    public BelongsToCollection belongsToCollection;
//    @SerializedName("production_companies")
//    @Expose
//    public List<ProductionCompany> productionCompanies = null;
//    @SerializedName("production_countries")
//    @Expose
//    public List<ProductionCountry> productionCountries = null;


    public class Genre {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;

        @Override
        public String   toString() {
            return "Genre{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public class Casts {
        @SerializedName("cast")
        @Expose
        public List<Cast> cast = null;
        @SerializedName("crew")
        @Expose
        public List<Crew> crew = null;

        @Override
        public String toString() {
            return "Casts{" +
                    "cast=" + cast +
                    ", crew=" + crew +
                    '}';
        }
    }

    public class Cast {
        @SerializedName("cast_id")
        @Expose
        public Integer castId;
        @SerializedName("character")
        @Expose
        public String character;
        @SerializedName("credit_id")
        @Expose
        public String creditId;
        @SerializedName("gender")
        @Expose
        public Integer gender;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("order")
        @Expose
        public Integer order;
        @SerializedName("profile_path")
        @Expose
        public String profilePath;

        @Override
        public String toString() {
            return "Cast{" +
                    "castId=" + castId +
                    ", character='" + character + '\'' +
                    ", creditId='" + creditId + '\'' +
                    ", gender=" + gender +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", order=" + order +
                    ", profilePath='" + profilePath + '\'' +
                    '}';
        }
    }

    public class Crew {
        @SerializedName("credit_id")
        @Expose
        public String creditId;
        @SerializedName("department")
        @Expose
        public String department;
        @SerializedName("gender")
        @Expose
        public Integer gender;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("job")
        @Expose
        public String job;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("profile_path")
        @Expose
        public String profilePath;

        @Override
        public String toString() {
            return "Crew{" +
                    "creditId='" + creditId + '\'' +
                    ", department='" + department + '\'' +
                    ", gender=" + gender +
                    ", id=" + id +
                    ", job='" + job + '\'' +
                    ", name='" + name + '\'' +
                    ", profilePath='" + profilePath + '\'' +
                    '}';
        }
    }

    public class SpokenLanguage {
        @SerializedName("iso_639_1")
        @Expose
        public String iso6391;
        @SerializedName("name")
        @Expose
        public String name;

        @Override
        public String toString() {
            return "SpokenLanguage{" +
                    "iso6391='" + iso6391 + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MovieDetails{" +
                "adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", id=" + id +
                ", imdbId='" + imdbId + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", spokenLanguages=" + spokenLanguages +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                ", casts=" + casts +
                '}';
    }
}