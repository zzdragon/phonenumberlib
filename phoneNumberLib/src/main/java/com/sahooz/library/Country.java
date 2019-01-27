package com.sahooz.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by android on 17/10/17.
 */

public class Country implements PyEntity {
    private static final String TAG = Country.class.getSimpleName();
    public int code;
    public String name, locale, py;
    public int flag;
    private static ArrayList<Country> countries = null;

    public Country(int code, String name, String locale, String py, int flag) {
        this.code = code;
        this.name = name;
        this.py = py;
        this.flag = flag;
        this.locale = locale;
        System.out.println("app country: " + toString());
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                "flag='" + flag + '\'' +
                ", name='" + name + '\'' +
                ", py='" + py + '\'' +
                '}';
    }

    public static ArrayList<Country> getAll(@NonNull Context ctx, @Nullable ExceptionCallback callback) {
        if (countries != null) return countries;
        countries = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(ctx.getResources().getAssets().open("number.json")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null)
                sb.append(line);
            br.close();
            JSONArray ja = new JSONArray(sb.toString());
            String key = getKey(ctx);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                int flag = 0;
                String locale = jo.optString("locale");
                String py;
                if (!TextUtils.isEmpty(locale)) {
                    flag = ctx.getResources().getIdentifier("flag_" + locale.toLowerCase(), "drawable", ctx.getPackageName());
                }

                if (TextUtils.equals(key, "zh")) {
                    py = jo.getString("py");
                } else {
                    py = jo.getString(key);
                }
                int code = Integer.valueOf(jo.getString("code").substring(1, jo.getString("code").length()));
                countries.add(new Country(code, jo.getString(key), locale, py, flag));
            }

            Log.i(TAG, countries.toString());
        } catch (IOException e) {
            if (callback != null) callback.onIOException(e);
            e.printStackTrace();
        } catch (JSONException e) {
            if (callback != null) callback.onJSONException(e);
            e.printStackTrace();
        }
        return countries;
    }

    public static Country fromJson(String json) {
        if (TextUtils.isEmpty(json)) return null;
        try {
            JSONObject jo = new JSONObject(json);
            return new Country(jo.optInt("code"), jo.optString("name"), jo.optString("locale"), "", jo.optInt("flag"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJson() {
        return "{\"name\":\"" + name + "\", \"code\":" + code + ", \"flag\":" + flag + ",\"locale\":\"" + locale + "\"}";
    }

    public static void destroy() {
        countries = null;
    }

    private static String getKey(Context ctx) {
        String country = ctx.getResources().getConfiguration().locale.getCountry();
//        return "CN".equalsIgnoreCase(country) ? "zh"
//                : "TW".equalsIgnoreCase(country) ? "tw"
//                : "HK".equalsIgnoreCase(country) ? "tw"
//                : "en";
        return "CN".equalsIgnoreCase(country) ? "zh"
                : "en";
    }

    private static boolean inChina(Context ctx) {
        return "CN".equalsIgnoreCase(ctx.getResources().getConfiguration().locale.getCountry());
    }

    @Override
    public int hashCode() {
        return code;
    }

    @NonNull
    @Override
    public String getPy() {
        return py.substring(0, 1).toUpperCase();
    }
}
