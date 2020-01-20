package com.example.salma.doctolib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MedecinAdapter extends RecyclerView.Adapter<MedecinAdapter.MyViewHolder> {

        private Context mContext;
        private List<String> mData;

    public MedecinAdapter(Context mContext, List<String> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater mInflater=LayoutInflater.from(mContext);
            view=mInflater.inflate(R.layout.item_medecin,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final String nom = mData.get(position);
            holder.mLibelleTxt.setText(nom);


        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


        public static class MyViewHolder extends RecyclerView.ViewHolder{

            TextView mLibelleTxt;
            ImageView mImageView;

            public MyViewHolder(View itemView) {
                super(itemView);

                mLibelleTxt=(TextView)itemView.findViewById(R.id.nomMedecinEdit);

            }
        }


}
