package com.example.findmypet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.findmypet.R;
import com.example.findmypet.models.PetProfile;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetProfileListAdapter extends RecyclerView.Adapter<PetProfileListAdapter.PetProfileHolder> {
    private List<PetProfile> mPetProfiles = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public PetProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.petprofile_item,parent, false);
        mContext = parent.getContext();
        return new PetProfileHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetProfileHolder holder, int position) {
        PetProfile currentPetProfile = mPetProfiles.get(position);
        Glide.with(mContext)
                .load(currentPetProfile.getImage_url())
                .into(holder.imageViewPetPic);
        holder.textViewName.setText(currentPetProfile.getName());

    }

    @Override
    public int getItemCount() {
        return mPetProfiles.size();
    }

    public void setPetProfiles(List<PetProfile> petProfiles){
        this.mPetProfiles = petProfiles;
        notifyDataSetChanged();
    }

    class PetProfileHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewPetPic;
        private TextView textViewName;


        public PetProfileHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPetPic = itemView.findViewById(R.id.image_petprofile_list);
            textViewName = itemView.findViewById(R.id.text_name_petprofile_list  );
        }
    }
}