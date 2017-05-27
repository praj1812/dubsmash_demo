package com.dekhoapp.android.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by prajakti on 5/26/2017.
 */

public class MusicActivity extends BaseActivity {
    String TAG = "MusicActivity";
    MusicItemAdapter mMusicItemAdapter = new MusicItemAdapter();
    ArrayList mMusicObjs = new ArrayList();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.musicRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMusicItemAdapter = new MusicItemAdapter(mMusicObjs);
        mRecyclerView.setAdapter(mMusicItemAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        Intent intent = getIntent();
        HashMap hashMap = (HashMap) intent.getSerializableExtra("hashMap");
        System.out.println("hashmap" + hashMap);

        Collection<HashMap> musicObjs = hashMap.values();
        Log.d(TAG, "musicObjs: " + musicObjs);
        for (Object musicObj : musicObjs) {
            Log.d(TAG, "music: " + musicObj);
            mMusicObjs.add(musicObj);
            Log.d(TAG, "added type: " + musicObj.getClass());
            mMusicItemAdapter.notifyDataSetChanged();
        }
    }
}
