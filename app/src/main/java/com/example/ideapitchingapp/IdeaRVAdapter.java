package com.example.ideapitchingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IdeaRVAdapter extends RecyclerView.Adapter<IdeaRVAdapter.ViewHolder> {

    int lastPos = -1;
    private ArrayList<IdeaRVModel> ideaRVModelArrayList;
    private Context context;
    private IdeaClickInterface ideaClickInterface;

    public IdeaRVAdapter(ArrayList<IdeaRVModel> ideaRVModelArrayList, Context context, IdeaClickInterface ideaClickInterface) {
        this.ideaRVModelArrayList = ideaRVModelArrayList;
        this.context = context;
        this.ideaClickInterface = ideaClickInterface;
    }

    @NonNull
    @Override
    public IdeaRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.idea_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IdeaRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position){
        IdeaRVModel ideaRVModel = ideaRVModelArrayList.get(position);
        holder.ideaNameTV.setText(ideaRVModel.getIdeaName());
        holder.ideaUserTV.setText(ideaRVModel.getIdeaUser());
        Picasso.get().load(ideaRVModel.getIdeaImg()).into(holder.ideaIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ideaClickInterface.onIdeaClick(position);
            }
        });

    }

    private void setAnimation(View itemView , int position){
          if(position>lastPos){
              Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
              itemView.setAnimation(animation);
              lastPos = position;
          }
    }
    @Override
    public int getItemCount() {
        return ideaRVModelArrayList.size();
    }

    public interface IdeaClickInterface{
        void onIdeaClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView ideaNameTV , ideaUserTV;
        private ImageView ideaIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ideaNameTV = itemView.findViewById(R.id.idTVIdeaName);
            ideaUserTV = itemView.findViewById(R.id.idTVUser);
            ideaIV = itemView.findViewById(R.id.idIVIdea);
        }
    }
}
