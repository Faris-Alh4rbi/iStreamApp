package com.example.istream;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        String username = getIntent().getStringExtra("USERNAME");

        List<PlaylistItem> playlist = AppDatabase.getInstance(this)
                .playlistDao().getForUser(username);

        RecyclerView rv = findViewById(R.id.rvPlaylist);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PlaylistAdapter(playlist, url -> {
            // Send URL back to HomeActivity to play
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("USERNAME", username);
            intent.putExtra("PLAY_URL", url);
            startActivity(intent);
            finish();
        }));

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finishAffinity();
        });
    }
}