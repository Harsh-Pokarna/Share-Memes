package com.example.sharememes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.InflaterInputStream;

public class MainActivity extends AppCompatActivity {
    Button shareBtn, nextBtn;
    ImageView imageView;
    ProgressBar progressBar;
    private RequestQueue requestQueue;
    String url = "https://meme-api.herokuapp.com/gimme";
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shareBtn = findViewById(R.id.shareButton);
        nextBtn = findViewById(R.id.nextButton);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        requestQueue = Volley.newRequestQueue(this);
        next();
        nextBtn.setOnClickListener(v -> next());
        shareBtn.setOnClickListener(v -> share());

    }

    private void next(){
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                  link = response.getString("url");
                Glide.with(MainActivity.this).load(link).fitCenter().listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).into(imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        });
        requestQueue.add(request);
    }
    private void share(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Coooooooooll  " + link);
        Intent chooser = Intent.createChooser(intent, "Share this meme using....");
        startActivity(chooser);
    }

}