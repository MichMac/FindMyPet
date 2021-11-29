package com.example.findmypet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnnouncementsListAdapter extends RecyclerView.Adapter<AnnouncementsListAdapter.AnnouncementHolder> {
    private List<Announcement> mAnnouncements = new ArrayList<>();
    private Context mContext;
    private OnAnnouncementListener mOnAnnouncementListener;

    public AnnouncementsListAdapter(OnAnnouncementListener onAnnouncementListener){
        this.mOnAnnouncementListener = onAnnouncementListener;
    }

    @NonNull
    @Override
    public AnnouncementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_item,parent, false);
        mContext = parent.getContext();
        return new AnnouncementHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementHolder holder, int position) {
        Announcement currentAnnouncement = mAnnouncements.get(position);
        Glide.with(mContext)
                .load(currentAnnouncement.getPetImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivPetPic);
        holder.tvStatus.setText(currentAnnouncement.getStatus());
        holder.tvDate.setText(currentAnnouncement.getDate());
        holder.tvSpecie.setText(currentAnnouncement.getPetSpecie());
        holder.tvBreed.setText(currentAnnouncement.getPetBreed());
        holder.tvCountryCity.setText(currentAnnouncement.getCountry() + "," + currentAnnouncement.getCity());
        holder.tvProvince.setText(currentAnnouncement.getProvince());
        holder.tvStreet.setText(currentAnnouncement.getStreet());
    }

    @Override
    public int getItemCount() {return mAnnouncements.size();}

    class AnnouncementHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivPetPic;
        private TextView tvSpecie;
        private TextView tvBreed;
        private TextView tvCountryCity;
        private TextView tvProvince;
        private TextView tvStreet;
        private TextView tvStatus;
        private TextView tvDate;

        public AnnouncementHolder(@NonNull View itemView) {
            super(itemView);
            ivPetPic = itemView.findViewById(R.id.image_pet_announcement);
            tvSpecie = itemView.findViewById(R.id.specie_pet_announcement_textview);
            tvBreed = itemView.findViewById(R.id.breed_pet_announcement_textview);
            tvCountryCity = itemView.findViewById(R.id.country_city_announcement_textview);
            tvProvince = itemView.findViewById(R.id.province_announcement_textview);
            tvStreet = itemView.findViewById(R.id.street_announcement_textview);
            tvStatus = itemView.findViewById(R.id.status_announcement_textview);
            tvDate = itemView.findViewById(R.id.date_announcement_textview);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {mOnAnnouncementListener.OnAnnouncementClick(getAdapterPosition());}
    }

    public void setAnnouncements(List<Announcement> announcements){
        this.mAnnouncements = announcements;
        notifyDataSetChanged();
    }

    public Announcement getSelectedAnnouncement(int position){
        if(mAnnouncements != null){
            if(mAnnouncements.size() > 0){
                return mAnnouncements.get(position);
            }
        }
        return null;
    }

}
