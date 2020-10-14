package com.example.empower.ui.about;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;

import java.util.List;

public class MainAdapter extends BaseAdapter<MainAdapter.ViewHolder> {

    private List<String> mDataList;

    public MainAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_liked_venue, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvName;
        TextView tvPostcode;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPostcode = itemView.findViewById(R.id.tv_postcode);
        }

        public void setData(String title) {
            String[] totalString = title.split(";");

            String selectedLikedVenueID = totalString[0].replace("venueID=", "");
            String selectedLikedVenueName = totalString[1].replace("name=", "");
            String selectedLikedVenuePostcode = totalString[2].replace("postcode=", "");
            this.tvTitle.setText(selectedLikedVenueID);
            this.tvName.setText(selectedLikedVenueName);
            this.tvPostcode.setText(selectedLikedVenuePostcode);
        }
    }

}
