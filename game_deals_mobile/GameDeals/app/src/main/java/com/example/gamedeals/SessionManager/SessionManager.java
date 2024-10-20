package com.example.gamedeals.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.gamedeals.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int Private_mode = 0;

    private static final String PREF_NAME = "SessionPreferences";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_EMAIL = "email";

    public static final String KEY_PASSWORD = "password";

    public static final String KEY_TOKEN = "token";

    public static final String USER_ID = "user_id";

    public static final String KEY_USERNAME = "username";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";


    public SessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Private_mode);
        editor = pref.edit();
    }

    public void createLoginSession(String email, String password, String token, String username, String first_name, String last_name){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_TOKEN, token);

        editor.putString(KEY_USERNAME, username);

        editor.putString(FIRST_NAME, first_name);
        editor.putString(LAST_NAME, last_name);
        editor.commit();

        Log.d("logi", first_name +"/" + last_name +"/" + username);
    }
    public boolean isLoggedIn(){

        /** sprawdzamy czy się zalogował samym loginem i hasłem */

        //return pref.getBoolean(IS_LOGIN, false);

        /** sprawdzamy czy się zalogował samym loginem i hasłem + czy jest aktywny token **/

        return pref.getBoolean(IS_LOGIN, false) && !pref.getString(KEY_TOKEN, "").isEmpty();
    }

    /** Jeżeli użytkownik nie jest zalogowany, uruchamia ekran logowania **/

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL,null));

        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        return user;
    }

    /** Pobranie wartości tokena **/

    public String getToken() {return pref.getString(KEY_TOKEN, ""); }
    public String getUserId() {return pref.getString(USER_ID, ""); }
    public String getStrings() {
        return pref.getString(LAST_NAME, "") + pref.getString(LAST_NAME, "");
    }
    /** Zakończenie sesji, wylogowanie użytkownika **/

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent (_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
}

