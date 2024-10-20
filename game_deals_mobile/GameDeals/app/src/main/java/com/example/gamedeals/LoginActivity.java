package com.example.gamedeals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.VolleyError;
import com.example.gamedeals.RequestsManager.RequestsManager;
import com.example.gamedeals.SessionManager.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    RequestsManager requestsManager;
    JSONObject userData;
    String url = "http://10.0.2.2:8000/api/login/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        requestsManager = new RequestsManager();
        sessionManager = new SessionManager(getApplicationContext());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void postLogin(View www) throws JSONException {
        EditText tekstemail = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText teksthaslo = (EditText) findViewById(R.id.editTextTextPassword);
        String email = tekstemail.getText().toString();
        String password = teksthaslo.getText().toString();
        JSONObject loginPayload = new JSONObject();
        JSONObject headerPayload = new JSONObject();
        loginPayload.put("username", email);
        loginPayload.put("password", password);
        headerPayload.put("Content-Type", "application/json");


        requestsManager.sendCall(url, this, headerPayload, loginPayload, new RequestsManager.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
            userData = result;
            createSession(userData);
            doMenu();
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Błąd: " + error, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void createSession(JSONObject userData)
    {
        try {
            /** Sprawdzenie czy z pustym tokenem sie zalogujemy**/
            String token = userData.getString("token");
            String password = userData.getString("user_id");
            String username = userData.getString("username");
            String email = userData.getString("email");
            String first_name = userData.getString("first_name");
            String last_name = userData.getString("last_name");

            /** Tworzenie sesji przez mqnagera **/
            sessionManager.createLoginSession(email, password, token, username, first_name, last_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void doMenu() {
        Intent Menu = new Intent(this, MainActivity.class);
        startActivity(Menu);
    }
}