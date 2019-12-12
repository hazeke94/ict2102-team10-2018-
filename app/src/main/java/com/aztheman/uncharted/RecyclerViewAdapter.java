package com.aztheman.uncharted;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aztheman.uncharted.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private ArrayList<String> mName = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext,ArrayList<Integer> mImageUrls, ArrayList<String> mName) {
        this.mImageUrls = mImageUrls;
        this.mName = mName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
    Log.d(TAG, "onbindViewHolder: called");

    Glide.with(mContext).asBitmap().load(mImageUrls.get(i)).into(viewHolder.mimage);
    viewHolder.mTitle.setText(mName.get(i));
    viewHolder.mimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("clicked on image");
            Toast.makeText(mContext, mName.get(i), Toast.LENGTH_SHORT).show();

        }
    });

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mimage;
        TextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mimage = itemView.findViewById(R.id.ivItem);
            mTitle = itemView.findViewById(R.id.titleItem);
        }
    }
}
