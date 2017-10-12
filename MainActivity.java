package com.bestdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bestdb.instagram.InstagramApp;
import com.bestdb.instagram.InstagramSession;
import com.bestdb.model.AccessTokenResponse;
import com.bestdb.model.InstagramRequiestResponse;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "ff3e72d4e19843e2a4c2a68787d9ece0";
    public static final String CLIENT_SECRET = "bbbe79e1c898454c94a15203b272a5a8";
    public static final String CALLBACK_URL = "http://www.google.com";

    private String instagram_token;
    private String instagram_id;
    private String instagram_username;
    private String instagram_name;

    private InstagramApp instagramApp;

    private Button btnLogin;
    private Button btnGetImage;

    InstagramApp.OAuthAuthenticationListener listener = new InstagramApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            //  hideProgress();
            try {
                InstagramSession s = new InstagramSession(MainActivity.this);
                instagram_token = s.getAccessToken();
                instagram_id = instagramApp.getId();
                instagram_username = instagramApp.getUserName();
                instagram_name = instagramApp.getUserName();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFail(String error) {
            //hideProgress();
            try {
                Log.e("Instagram-error", error + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm realm = Realm.getDefaultInstance();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGetImage = (Button) findViewById(R.id.btnGetImage);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instagramApp = new InstagramApp(MainActivity.this, CLIENT_ID,CLIENT_SECRET, CALLBACK_URL);
                instagramApp.setListener(listener);
                if (instagramApp.hasAccessToken()) {
                    instagramApp.resetAccessToken();
                }
                instagramApp.authorize();
            }
        });

        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages(InstagramApp.mAccessToken);
            }
        });

        try {
            realm.beginTransaction();
            User user = realm.createObject(User.class, UUID.randomUUID().toString());
            user.setFirstName("nada");
            user.setLastName("satish");
            User user2 = realm.createObject(User.class, UUID.randomUUID().toString());
            user2.setFirstName("nada");
            user2.setLastName("satish");
            User user3 = realm.createObject(User.class, UUID.randomUUID().toString());
            user3.setFirstName("nada2");
            user3.setLastName("satish");
            User user4 = realm.createObject(User.class, UUID.randomUUID().toString());
            user4.setFirstName("nada1");
            user4.setLastName("satish");
            realm.commitTransaction();

            RealmResults<User> users = realm.where(User.class).findAll();
            Log.i("REALM_DB","Users Size "+users.size());
            Log.i("REALM_DB","Users Size "+users.get(0).getFirstName());

        } finally {
            realm.close();
        }

    }

    private void getImages(final String mAccessToken) {
        try {
            if (Utility.isNetworkAvailable(MainActivity.this)) {
                Utility.showProgress(MainActivity.this);

                RestServicesRequest.ConsumerApiInterface service = RestServicesRequest.getClient();
                Call<InstagramRequiestResponse> call = service.getImages(mAccessToken);

                call.enqueue(new Callback<InstagramRequiestResponse>() {
                    @Override
                    public void onResponse(Call<InstagramRequiestResponse> call, Response<InstagramRequiestResponse> response) {
                        if (response.isSuccessful()) {
                            final InstagramRequiestResponse result = response.body();

                        }
                    }

                    @Override
                    public void onFailure(Call<InstagramRequiestResponse> call, Throwable t) {
                        t.printStackTrace();
                        Utility.hideProgress();
                    }
                });

            } else {
                Utility.hideProgress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
