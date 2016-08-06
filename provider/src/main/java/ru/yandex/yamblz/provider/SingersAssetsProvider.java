package ru.yandex.yamblz.provider;

import android.content.Context;
import android.support.v4.database.DatabaseUtilsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SingersAssetsProvider {

    private Context mContext;

    public SingersAssetsProvider(Context context) {
        mContext = context;
    }

    public List<Singer> getSingers() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(mContext.getAssets()
                    .open("singers.json")));
            return new Gson().fromJson(reader, new TypeToken<List<Singer>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
