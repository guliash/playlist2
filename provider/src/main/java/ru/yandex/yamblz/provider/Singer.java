package ru.yandex.yamblz.provider;

import java.util.List;

public class Singer {

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

    public class Builder {
        private final String name;
        private final List<String> genres;
        private final Cover cover;

        private int id = 0;
        private int tracks = 0;
        private int albums = 0;
        private String link;
        private String description;

        public Builder(String name, List<String> genres, Cover cover) {
            this.name = name;
            this.genres = genres;
            this.cover = cover;
        }

        public Builder id(int id) {
            this.id = id;
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
