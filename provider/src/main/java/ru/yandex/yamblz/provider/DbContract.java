package ru.yandex.yamblz.provider;

/**
 * Contract for the database
 */
public final class DbContract {
    public static final String DB_NAME = "singers.sqlite";

    public static class Singers {
        public static final String TABLE_NAME = "singers";

        //TYPE: INTEGER
        public static final String ID = "id";

        //TYPE: TEXT
        public static final String NAME = "name";

        //TYPE: INTEGER
        public static final String TRACKS = "tracks";

        //TYPE: INTEGER
        public static final String ALBUMS = "albums";

        //TYPE: TEXT
        public static final String DESCRIPTION = "description";

        //TYPE: TEXT
        public static final String COVER_SMALL = "cover_small";

        //TYPE: TEXT
        public static final String COVER_BIG = "cover_big";

        public static final String NAME_INDEX = TABLE_NAME + "_name_index";
    }

    public static class Genres {
        public static final String TABLE_NAME = "genres";

        //TYPE: INTEGER
        public static final String ID = "id";

        //TYPE: TEXT
        public static final String NAME = "name";
    }

    public static final class SingersGenres {
        public static final String TABLE_NAME = "singers_genres";

        //TYPE: INTEGER
        public static final String SINGER_ID = "singer_id";

        //TYPE: INTEGER
        public static final String GENRE_ID = "genre_id";
    }

}
