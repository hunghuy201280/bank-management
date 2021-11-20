package com.example.bankmanagement.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.Map;

public class Preferences {

    private static Preferences instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Preferences() {
    }

    public static Preferences getInstance() {
        if (instance == null) {
            synchronized (Preferences.class) {
                if (instance == null) {
                    instance = new Preferences();
                }
            }
        }
        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Store value to SharedPreferences
     */
    public void storeValue(String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        if (value instanceof Double) {
            editor.putLong(key, Double.doubleToRawLongBits((Double) value));
        }
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        if (value instanceof Parcelable) {
            Gson gson = new Gson();
            String stringObject = gson.toJson(value);
            editor.putString(key, stringObject);
        }
        if (value == null) {
            editor.remove(key);
        }
        editor.apply();
    }

    /**
     * Get String
     */
    public synchronized String getString(String key) {
        return getString(key, null);
    }

    /**
     * Get String with default value
     */
    public synchronized String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * Get Int
     *
     * @param key
     * @return
     */
    public synchronized int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Get Int with default value
     */
    public synchronized int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * Get Long
     *
     * @param key
     * @return
     */
    public synchronized long getLong(String key) {
        return getLong(key, 0);
    }

    /**
     * Get Long with default value
     *
     * @param key
     * @param defValue
     * @return
     */
    public synchronized long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    /**
     * Get Float
     *
     * @param key
     * @return
     */
    public synchronized float getFloat(String key) {
        return getFloat(key, 0);
    }

    /**
     * Get Float with default value
     *
     * @param key
     * @param defValue
     * @return
     */
    public synchronized float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    /**
     * Get Double
     *
     * @param key
     * @return
     */
    public synchronized double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    /**
     * Get Double with default value
     *
     * @param key
     * @param defValue
     * @return
     */
    public synchronized double getDouble(String key, double defValue) {
        return Double.longBitsToDouble(getLong(key, Double.doubleToLongBits(defValue)));
    }

    /**
     * Get Boolean
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Get Boolean with default value
     */
    public synchronized boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * Get Parcelable
     */
    @Nullable
    public synchronized <T extends Parcelable> T getParcelableObject(String key, Class<T> type) {
        Gson gson = new Gson();
        String string = sharedPreferences.getString(key, null);
        if (string == null) {
            return null;
        }
        return gson.fromJson(string, type);
    }

    /**
     * Clear all preferences
     */
    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    /**
     * Clear all preferences except some keys
     */
    public void clearAllExcepts(String... keys) {
        Map<String, ?> prefs = sharedPreferences.getAll();
        for (Map.Entry<String, ?> prefKey : prefs.entrySet()) {
            for (String exceptKey : keys) {
                String keyToReset = prefKey.getKey();
                if (!keyToReset.equals(exceptKey)) {
                    sharedPreferences.edit()
                            .remove(keyToReset)
                            .apply();
                }
            }
        }
    }
}
