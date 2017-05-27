package com.dekhoapp.android.app.base;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dekhoapp.android.app.base.utils.FirebaseDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by prajakti on 5/25/2017.
 */

public class CategoryActivity extends BaseActivity {
    private static final String TAG = "CategoryActivity";
    private ArrayList<String> mCategories = new ArrayList<>();
    private CategoryItemAdapter mCategoryItemAdapter = new CategoryItemAdapter();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCategoryItemAdapter = new CategoryItemAdapter(mCategories);
        mRecyclerView.setAdapter(mCategoryItemAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        getMusic();
    }

    public void getMusic() {
        DatabaseReference myRef = FirebaseDatabaseUtil.getBaseRef().child("Prajakti").child("Music");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap hashMap = (HashMap) dataSnapshot.getValue();
                    Log.d(TAG, "hashMap: " + hashMap.toString());

                    Collection hashMapKeys = hashMap.keySet();
                    Log.d(TAG, "hashMapKeys: " + hashMapKeys.toString());

                    for (Object key : hashMapKeys) {
                        Log.d(TAG, "key: " + key);
                        mCategories.add(key.toString());
                        mCategoryItemAdapter.notifyDataSetChanged();
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "database error" + databaseError.getDetails());
                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

//        final DatabaseReference myRoot = FirebaseDatabaseUtil.getBaseRef().child("Prajakti").child("Music").child("Melody");
//        System.out.println(myRoot.toString());
//
//        Map<String, Object> musicObj = new HashMap<>();
//        musicObj.put("title", "title");
//        musicObj.put("uri", "uri");
//
//        System.out.println("musicObj: " + musicObj.toString());
//        myRoot.push().setValue(musicObj);

    }
}
