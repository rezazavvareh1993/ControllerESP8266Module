package com.example.controllerEsp8266Module.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllerEsp8266Module.DataBase.Tables.Devices;
import com.example.controllerEsp8266Module.R;

import java.util.ArrayList;
import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {
    private List<Devices> devices = new ArrayList<>();
    private OnItemTitleClickListener nameListener;
    private OnItemClickListener listener;

    public DevicesAdapter() {
    }


    @NonNull
    @Override
    public DevicesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.devices_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DevicesAdapter.MyViewHolder holder, int position) {

        Devices currentRoomDevices = devices.get(position);
        holder.txtNameItems.setText(currentRoomDevices.getNameItem());
        holder.imgDevice.setImageResource(currentRoomDevices.getImgItem());
        holder.imgEditNameItem.setImageResource(currentRoomDevices.getImgEditItem());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void setItems(List<Devices> devices) {
        this.devices = devices;
        notifyDataSetChanged();
    }

    public Devices getRoomAt(int position) {
        return devices.get(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameItems;
        private ImageView imgDevice, imgEditNameItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameItems = itemView.findViewById(R.id.txt_name_room_devices);
            imgDevice = itemView.findViewById(R.id.img_room_devices_item);
            imgEditNameItem = itemView.findViewById(R.id.img_rooms_devices_edit_title);

            imgEditNameItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (nameListener != null && position != RecyclerView.NO_POSITION) {
                        nameListener.onItemTitleClick(devices.get(position));
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(devices.get(position));
                    }
                }
            });

        }
    }


    public interface OnItemTitleClickListener {
        void onItemTitleClick(Devices devices);
    }

    public void setOnItemNameClickListener(DevicesAdapter.OnItemTitleClickListener nameListener) {
        this.nameListener = nameListener;

    }

    public interface OnItemClickListener {
        void onItemClick(Devices devices);
    }

    public void setOnItemClickListener(DevicesAdapter.OnItemClickListener listener) {
        this.listener = listener;

    }
}
