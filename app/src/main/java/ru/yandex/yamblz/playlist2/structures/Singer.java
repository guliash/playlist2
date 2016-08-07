package ru.yandex.yamblz.playlist2.structures;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Singer implements Parcelable {

    private int id;

    private String name;

    private List<String> genres;

    private int tracks;

    private int albums;

    private String link;

    private String description;

    private Cover cover;

    private Singer(Builder builder) {
        id = builder.id;
        name = builder.name;
        genres = builder.genres;
        tracks = builder.tracks;
        albums = builder.albums;
        link = builder.link;
        description = builder.description;
        cover = builder.cover;
    }

    protected Singer(Parcel in) {
        id = in.readInt();
        name = in.readString();
        genres = in.createStringArrayList();
        tracks = in.readInt();
        albums = in.readInt();
        link = in.readString();
        description = in.readString();
    }

    public static final Creator<Singer> CREATOR = new Creator<Singer>() {
        @Override
        public Singer createFromParcel(Parcel in) {
            return new Singer(in);
        }

        @Override
        public Singer[] newArray(int size) {
            return new Singer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeStringList(genres);
        dest.writeInt(tracks);
        dest.writeInt(albums);
        dest.writeString(link);
        dest.writeString(description);
    }

    public static class Builder {
        private int id = 0;
        private String name;
        private int tracks = 0;
        private int albums = 0;
        private List<String> genres;
        private Cover cover;
        private String link;
        private String description;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder genres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public Builder cover(Cover cover) {
            this.cover = cover;
            return this;
        }

        public Builder tracks(int tracks) {
            this.tracks = tracks;
            return this;
        }

        public Builder albums(int albums) {
            this.albums = albums;
            return this;
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Singer build() {
            return new Singer(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Cover getCover() {
        return cover;
    }

    @Override
    public String toString() {
        return name;
    }

}
