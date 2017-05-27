package com.dekhoapp.android.app.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prajakti on 5/26/2017.
 */

public class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.ViewHolder>{
    String TAG = "MusicItemAdapter";
    private ArrayList<HashMap> musicObjs = new ArrayList<>();

    public MusicItemAdapter(ArrayList<HashMap> mMusicObjs) {
        this.musicObjs = mMusicObjs;
    }

    public MusicItemAdapter() {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.musicTextView);
        }
    }

    @Override
    public MusicItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View musicView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_item, parent, false);

        return new ViewHolder(musicView);
    }

    @Override
    public void onBindViewHolder(final MusicItemAdapter.ViewHolder holder, int position) {
        final HashMap musicObj = musicObjs.get(position);
        Log.d(TAG, "musicObj: " + musicObj.toString());
        Log.d(TAG, "title: " + musicObj.get("title").toString());
        holder.textView.setText(musicObj.get("title").toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AccessCameraActivity.class);
                String uriStr = musicObj.get("uri").toString();
                intent.putExtra("uriStr", uriStr);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicObjs.size();
    }
}
