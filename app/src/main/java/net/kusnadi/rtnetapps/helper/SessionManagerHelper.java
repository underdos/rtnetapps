package net.kusnadi.rtnetapps.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import net.kusnadi.rtnetapps.entity.User;

/**
 * Created by root on 24/09/17.
 */

public class SessionManagerHelper {
    private final String PREF = "session";
    private final int MOD = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManagerHelper(Context context){
        this.context = context;
        this.sharedPreferences = this.context.getSharedPreferences(PREF, MOD);
        this.editor = this.sharedPreferences.edit();
    }

    public void createSession(String email, User user){
        Gson gson = new Gson();
        editor.putBoolean("isLogin", true);
        editor.putString("username", email);
        editor.putString("userdata", gson.toJson(user));
        editor.commit();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean("isLogin", false);
    }

    public User getSessionData(){
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString("userdata", ""),User.class);
        return user;
    }

    public void clearSession(){
        editor.clear();
        editor.commit();
    }

}
