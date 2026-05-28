package com.example.istream;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    String currentUsername;
    WebView webView;
    EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUsername         = getIntent().getStringExtra("USERNAME");

        TextView tvWelcome      = findViewById(R.id.tvWelcome);
        etUrl                   = findViewById(R.id.etUrl);
        webView                 = findViewById(R.id.webView);
        Button btnPlay          = findViewById(R.id.btnPlay);
        Button btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist);
        Button btnMyPlaylist    = findViewById(R.id.btnMyPlaylist);
        Button btnLogout        = findViewById(R.id.btnLogout);

        tvWelcome.setText("Welcome, " + currentUsername + "!");

        // WebView setup
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // If coming from playlist screen with a URL to play
        String playUrl = getIntent().getStringExtra("PLAY_URL");
        if (playUrl != null) {
            etUrl.setText(playUrl);
            String videoId = extractVideoId(playUrl);
            if (videoId != null) playVideo(videoId);
        }

        btnPlay.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            String videoId = extractVideoId(url);
            if (videoId == null) {
                Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }
            playVideo(videoId);
        });

        btnAddToPlaylist.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(this, "Enter a URL first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (extractVideoId(url) == null) {
                Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }
            PlaylistItem item = new PlaylistItem();
            item.username = currentUsername;
            item.url      = url;
            AppDatabase.getInstance(this).playlistDao().insert(item);
            Toast.makeText(this, "Added to playlist!", Toast.LENGTH_SHORT).show();
        });

        btnMyPlaylist.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlaylistActivity.class);
            intent.putExtra("USERNAME", currentUsername);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    void playVideo(String videoId) {
        String html = "<!DOCTYPE html><html><body style='margin:0;padding:0;background:#000;'>" +
                "<iframe width='100%' height='100%' " +
                "src='https://www.youtube-nocookie.com/embed/" + videoId + "?playsinline=1&rel=0' " +
                "frameborder='0' " +
                "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' " +
                "allowfullscreen>" +
                "</iframe></body></html>";
        webView.loadDataWithBaseURL(
                "https://www.youtube-nocookie.com",
                html,
                "text/html",
                "utf-8",
                null
        );
    }

    String extractVideoId(String url) {
        if (url == null || url.isEmpty()) return null;
        // Handle youtube.com/watch?v=ID
        if (url.contains("v=")) {
            int start = url.indexOf("v=") + 2;
            int end   = url.indexOf("&", start);
            if (end == -1) end = url.length();
            return url.substring(start, end);
        }
        // Handle youtu.be/ID
        if (url.contains("youtu.be/")) {
            int start = url.indexOf("youtu.be/") + 9;
            int end   = url.indexOf("?", start);
            if (end == -1) end = url.length();
            return url.substring(start, end);
        }
        return null;
    }
}