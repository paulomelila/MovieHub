package com.paulomelila.moviehub;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Movie implements Parcelable {
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    private UUID mId;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("director")
    private String director;
    @SerializedName("genres")
    private List<String> genres;
    @SerializedName("cast")
    private List<String> castMembers;

    public Movie() {
        this.mId = UUID.randomUUID();
        this.genres = new ArrayList<>();
        this.castMembers = new ArrayList<>();
    }

    public Movie(UUID id) {
        this.mId = id;
        this.genres = new ArrayList<>();
        this.castMembers = new ArrayList<>();
    }

    protected Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
        String uuidString = in.readString();
        mId = (uuidString != null) ? UUID.fromString(uuidString) : UUID.randomUUID();
        releaseDate = in.readString();
        director = in.readString();
        genres = in.createStringArrayList();
        castMembers = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(description);
        dest.writeString(backdrop);
        dest.writeString(mId != null ? mId.toString() : null);
        dest.writeString(releaseDate);
        dest.writeString(director);
        dest.writeStringList(genres);
        dest.writeStringList(castMembers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public UUID getId() { return mId; }
    public void setId(UUID mId) { this.mId = mId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return "https://image.tmdb.org/t/p/w500" + poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBackdrop() { return "https://image.tmdb.org/t/p/w500" + backdrop; }
    public void setBackdrop(String backdrop) { this.backdrop = backdrop; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; } // Added setter
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; } // Added setter
    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; } // Added setter
    public List<String> getCastMembers() { return castMembers; }
    public void setCastMembers(List<String> castMembers) { this.castMembers = castMembers; } // Added setter


    public static class MovieResult {
        private List<Movie> results;
        public List<Movie> getResults() { return results; }
    }

    public String getURL() { return poster; }
}
