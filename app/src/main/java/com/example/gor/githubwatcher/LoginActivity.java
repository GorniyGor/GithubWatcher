package com.example.gor.githubwatcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gor.githubwatcher.di.AppComponent;
import com.example.gor.githubwatcher.model.AccessToken;
import com.example.gor.githubwatcher.service.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private final static String AUTH_URL = "https://github.com/login/oauth/authorize";
    private final String clientId = "408ac7d0ff6657b26304";
    private final String clientSecret = "c7954bf7c3c7be3acf5bda32e288f59e5e575c46";
    private final String redirectUri = "githubwatcher://callback";
    Button loginButton;

    AppComponent component;

    /*@Inject*/
    GitHubClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        component = App.component(this);
        /*component = DaggerAppComponent.builder()
                .webModule(new WebModule())
                .dataModule(new DataModule())
                .build();*/
        client = component.client();
        /*component.inject(this);*/

        loginButton = (Button) findViewById(R.id.id_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        AUTH_URL + "?client_id=" + clientId + "&scope=repo&redirect_uri=" + redirectUri));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(redirectUri)) {

            String code = uri.getQueryParameter("code");

            Call<AccessToken> accessTokenCall = client.getAccessToken(clientId, clientSecret, code);
            accessTokenCall.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, ListReposActivity.class);
                    intent.putExtra("access_token", response.body().getAccessToken());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            });

            finish();

        }

    }
}
