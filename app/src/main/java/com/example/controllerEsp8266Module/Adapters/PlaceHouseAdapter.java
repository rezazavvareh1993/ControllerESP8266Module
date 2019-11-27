package com.example.controllerEsp8266Module.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllerEsp8266Module.Activities.RoomActivity;
import com.example.controllerEsp8266Module.Models.PlaceHouseModel;
import com.example.controllerEsp8266Module.R;

import java.util.List;

public class PlaceHouseAdapter extends RecyclerView.Adapter<PlaceHouseAdapter.myViewHolder> {
    private Context context;

    private List<PlaceHouseModel> mData;
    private LayoutInflater mInflater;
    private OnItemClickListener listener;

    public PlaceHouseAdapter(Context context, List<PlaceHouseModel> mData) {
        this.mData = mData;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.smart_house_list_item, parent, false);
        myViewHolder holder = new myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        PlaceHouseModel currentObject = mData.get(position);
        holder.setData(currentObject, position);
        holder.setListeners();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        private TextView namePlace;
        private ImageView imgPlace;
        int position;
        private boolean checkClickEdtViewItem = false;
        PlaceHouseModel placeHouseModel;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            namePlace = itemView.findViewById(R.id.edtNamePlace);
            imgPlace = itemView.findViewById(R.id.imgPlace);
        }

        public void setData(PlaceHouseModel currentObject, int position) {

            this.namePlace.setText(currentObject.getTxtPlace());
            this.imgPlace.setImageResource(currentObject.getImgPlace());
            this.position = position;
            this.placeHouseModel = currentObject;
        }

        public void setListeners() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mData.get(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PlaceHouseModel placeHouseModel, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
