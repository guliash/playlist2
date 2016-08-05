package ru.yandex.yamblz.playlist2;

import android.net.Uri;

public final class SingersContract {

    public static final String AUTHORITY = "ru.yandex.yamblz.provider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class Singers {

        static final String TABLE_NAME = "singers";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/singers");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ru.yandex.yamblz.provider.singers";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.ru.yandex.yamblz.provider.singers";

        /**
         * ID column
         * TYPE: INTEGER
         */
        public static final String ID = "_id";

        /**
         * Name column
         * TYPE: TEXT
         */
        public static final String NAME = "name";

        /**
         * Tracks column
         * TYPE: INTEGER
         */
        public static final String TRACKS = "tracks";

        /**
         * Albums column
         * TYPE: INTEGER
         */
        public static final String ALBUMS = "albums";

        /**
         * Description column
         * TYPE: TEXT
         */
        public static final String DESCRIPTION = "description";

        /**
         * Cover small column
         * TYPE: TEXT
         */
        public static final String COVER_SMALL = "cover_small";

        /**
         * Cover big column
         * TYPE: TEXT
         */
        public static final String COVER_BIG = "cover_big";

    }

    public static class Genres {

        static final String TABLE_NAME = "genres";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/genres");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ru.yandex.yamblz.provider.genres";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.ru.yandex.yamblz.provider.genres";

        /**
         * ID column
         * TYPE: INTEGER
         */
        public static final String ID = "id";

        /**
         * ID column
         * TYPE: text
         */
        public static final String GENRE = "genre";
    }

}