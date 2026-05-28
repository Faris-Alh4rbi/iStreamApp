package com.example.istream;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.VH> {

    List<PlaylistItem> list;
    OnUrlClick listener;

    public interface OnUrlClick { void onClick(String url); }

    public PlaylistAdapter(List<PlaylistItem> list, OnUrlClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PlaylistItem item = list.get(position);
        holder.tvUrl.setText(item.url);
        holder.itemView.setOnClickListener(v -> listener.onClick(item.url));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvUrl;
        VH(View v) {
            super(v);
            tvUrl = v.findViewById(R.id.tvUrl);
        }
    }
}