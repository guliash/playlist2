package ru.yandex.yamblz.provider;

import android.database.Cursor;
import android.support.annotation.NonNull;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.yandex.yamblz.singerscontracts.Cover;
import ru.yandex.yamblz.singerscontracts.Singer;
import ru.yandex.yamblz.singerscontracts.SingersContract;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class ,manifest = Config.NONE)
public class DbBackendTest {

    private List<Singer> mSingers = new ArrayList<>();
    private List<String> mGenres = new ArrayList<>();
    @NonNull private DbOpenHelper mDbOpenHelper;
    @NonNull private DbBackend mDbBackend;

    @Before
    public void init() {
        mSingers.add(getSinger(1));
        mSingers.add(getSinger(2));
        mSingers.add(getSinger(0));
        mSingers.add(getSinger(-323));

        mGenres.add("TEST GENRE 1");
        mGenres.add("TEST GENRE 2");

        mDbOpenHelper = new DbOpenHelper(RuntimeEnvironment.application);
        mDbBackend = new DbBackend(mDbOpenHelper);
    }

    private Singer getSinger(int seed) {
        Singer.Builder builder = new Singer.Builder();
        builder.id(seed);
        builder.name("Name " + seed);
        builder.albums(seed);
        builder.tracks(seed);
        builder.description("desc " + seed);
        builder.cover(new Cover("small " + seed, "big " + seed));
        List<String> genres = new ArrayList<>();
        genres.add("genre " + (seed + 1));
        genres.add("genre " + (seed + 2));
        builder.genres(genres);
        return builder.build();
    }

    @Test
    public void testAllSingersWereInsertedDuringMigration() {
        mDbBackend.migrateSingers(mSingers);
        Cursor cursor = mDbBackend.getSingers(null, null, null,null);
        Assert.assertEquals(false, cursor == null);
        Assert.assertEquals(cursor.getCount(), mSingers.size());
        cursor.close();
    }

    @Test
    public void testAllSingersFieldsWereInsertedDuringMigration() {
        mDbBackend.migrateSingers(mSingers);
        Cursor cursor = mDbBackend.getSingers(null, null, null, SingersContract.Singers.NAME + " ASC");

        Collections.sort(mSingers, new Comparator<Singer>() {
            @Override
            public int compare(Singer lhs, Singer rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        List<Singer> bdSingers = Singer.readSingers(cursor);
        Assert.assertEquals(true, mSingers.equals(bdSingers));
        cursor.close();
    }

    @Test
    public void testGetSingersProjectionWork() {
        mDbBackend.migrateSingers(mSingers);
        String[] proj = new String[] {
                SingersContract.Singers.ALBUMS,
                SingersContract.Singers.TRACKS,
                SingersContract.Singers.NAME
        };

        Cursor cursor = mDbBackend.getSingers(proj, null, null, null);
        Assert.assertEquals(cursor.getColumnCount(), proj.length);
    }

    @Test
    public void testGetSingersSelectionWork() {
        mDbBackend.migrateSingers(mSingers);
        String selection = SingersContract.Singers.NAME + "=?";
        String arg = mSingers.get(0).getName();
        String[] selectionArgs = new String[] { arg };
        Cursor cursor = mDbBackend.getSingers(null, selection, selectionArgs, null);
        List<Singer> dbSingers = Singer.readSingers(cursor);
        for(Singer singer : dbSingers) {
            Assert.assertEquals(true, singer.getName().equals(arg));
        }
    }

    @Test
    public void testInsertGenreWorks() {
        Assert.assertEquals(true, mDbBackend.insertGenre(mGenres.get(0)));
        Assert.assertEquals(true, mDbBackend.selectGenreId(mGenres.get(0)) != -1);
    }

    @Test
    public void testInsertGenresWorks() {
        Assert.assertEquals(true, mDbBackend.insertGenres(mGenres));
        for(String genre : mGenres) {
            Assert.assertEquals(true, mDbBackend.selectGenreId(genre) != -1);
        }
    }

    @Test
    public void testInsertSingerWorks() {
        Singer singer = mSingers.get(0);
        Assert.assertEquals(true, mDbBackend.insertSinger(singer));
        Cursor cursor = mDbBackend.getSingers(null, SingersContract.Singers.NAME + "=?",
                new String[] {singer.getName()}, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testInsertSingersWorks() {
        Assert.assertEquals(true, mDbBackend.insertSingers(mSingers));
        Cursor cursor = mDbBackend.getSingers(null, null, null, SingersContract.Singers.NAME + " ASC");
        Assert.assertEquals(mSingers.size(), cursor.getCount());

        Collections.sort(mSingers, new Comparator<Singer>() {
            @Override
            public int compare(Singer lhs, Singer rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });

        List<Singer> bdSingers = Singer.readSingers(cursor);
        for(int i = 0; i < mSingers.size(); i++) {
            Assert.assertEquals(true, mSingers.get(i).getName().equals(bdSingers.get(i).getName()));
        }
    }

}
