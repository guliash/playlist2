package ru.yandex.yamblz.singerscontracts;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        cover = in.readParcelable(Cover.class.getClassLoader());
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
        dest.writeParcelable(cover, 0);
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
    public boolean equals(Object o) {
        if(!(o instanceof Singer)) {
            return false;
        }
        Singer singer = (Singer)o;
        return singer.getName().equals(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<String> extractGenres(List<Singer> singers) {
        Set<String> genres = new HashSet<>();
        for(Singer singer : singers) {
            genres.addAll(singer.getGenres());
        }
        return new ArrayList<>(genres);
    }

    public static Singer readSinger(@NonNull Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must not be null");
        }
        Singer.Builder builder = new Singer.Builder();
        String genresConcat = cursor.getString(cursor.getColumnIndex(SingersContract.Singers.GENRES));
        List<String> genres = null;
        if(!TextUtils.isEmpty(genresConcat)) {
            genres = Arrays.asList(TextUtils.split(genresConcat, ","));
        }
        builder.id(cursor.getInt(cursor.getColumnIndex(SingersContract.Singers.ID)))
                .genres(genres)
                .name(cursor.getString(cursor.getColumnIndex(SingersContract.Singers.NAME)))
                .tracks(cursor.getInt(cursor.getColumnIndex(SingersContract.Singers.TRACKS)))
                .albums(cursor.getInt(cursor.getColumnIndex(SingersContract.Singers.ALBUMS)))
                .cover(new Cover(
                        cursor.getString(cursor.getColumnIndex(SingersContract.Singers.COVER_SMALL)),
                        cursor.getString(cursor.getColumnIndex(SingersContract.Singers.COVER_BIG))
                ))
                .description(cursor.getString(cursor.getColumnIndex(SingersContract.Singers.DESCRIPTION)));

        return builder.build();
    }

    public static List<Singer> readSingers(@NonNull Cursor cursor) {
        if(cursor == null) {
            throw new IllegalArgumentException("Cursor must not be null");
        }
        List<Singer> result = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            result.add(readSinger(cursor));
            cursor.moveToNext();
        }
        return result;
    }

}
