package com.dekhoapp.android.app.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dekhoapp.android.app.base.utils.FirebaseDatabaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by prajakti on 5/25/2017.
 */

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>{

    private String TAG = "CategoryItemAdapter";
    private ArrayList<String> categories = new ArrayList<>();

    public CategoryItemAdapter(ArrayList<String> mCategories) {
        this.categories = mCategories;
    }

    public CategoryItemAdapter() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.categoryTextView);
        }
    }

    @Override
    public CategoryItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View categoryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new ViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(final CategoryItemAdapter.ViewHolder holder, int position) {
        final String category = categories.get(position);
        Log.d(TAG, "text: " + category);
        holder.textView.setText(category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "clicked");
                holder.textView.setBackgroundColor(v.getResources().getColor(android.R.color.white));

                DatabaseReference myRef = FirebaseDatabaseUtil.getBaseRef().child("Prajakti").child("Music").child(category);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap hashMap = (HashMap) dataSnapshot.getValue();
                        Log.d(TAG, "hashMap " + hashMap);

                        Context context = v.getContext();
                        Intent intent =  new Intent(context, MusicActivity.class);
                        intent.putExtra("hashMap", hashMap);

                        holder.textView.setBackgroundColor(v.getResources().getColor(android.R.color.transparent));
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "failed");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
