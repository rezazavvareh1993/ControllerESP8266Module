package com.example.controllerEsp8266Module.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllerEsp8266Module.DataBase.Tables.Room;
import com.example.controllerEsp8266Module.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {
    private List<Room> rooms = new ArrayList<>();
    private OnItemTitleClickListener nameListener;
    private OnItemClickListener listener;

    public RoomAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Room currentRoom = rooms.get(position);
        holder.txtNameRoom.setText(currentRoom.getNameItem());
        holder.imgRoom.setImageResource(currentRoom.getImgItem());
        holder.imgEditRoom.setImageResource(currentRoom.getImgEdit());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void setItems(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

    public Room getRoomAt(int position) {
        return rooms.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameRoom;
        private ImageView imgRoom, imgEditRoom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameRoom = itemView.findViewById(R.id.txt_name_room_devices);
            imgEditRoom = itemView.findViewById(R.id.imgRoomEditTitle);
            imgRoom = itemView.findViewById(R.id.img_room_devices_item);

            imgEditRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (nameListener != null && position != RecyclerView.NO_POSITION) {
                        nameListener.onItemTitleClick(rooms.get(position));
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(rooms.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemTitleClickListener {
        void onItemTitleClick(Room room);
    }

    public void setOnItemNameClickListener(OnItemTitleClickListener nameListener) {
        this.nameListener = nameListener;

    }

    public interface OnItemClickListener {
        void onItemClick(Room room);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

}
