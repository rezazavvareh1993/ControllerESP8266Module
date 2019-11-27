package com.example.controllerEsp8266Module.Activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.controllerEsp8266Module.Adapters.RoomAdapter;
import com.example.controllerEsp8266Module.Constants;
import com.example.controllerEsp8266Module.DataBase.Tables.Room;
import com.example.controllerEsp8266Module.DataBase.ViewModels.RoomsViewModel;
import com.example.controllerEsp8266Module.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoomActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomsViewModel roomsViewModel;
    private FloatingActionButton fbAddRooms;
    private Room mLastDeletedRoom ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        fbAddRooms = findViewById(R.id.fb_add_room);

        recyclerView = findViewById(R.id.recycler_view_room);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final RoomAdapter adapter = new RoomAdapter();
        recyclerView.setAdapter(adapter);

        roomsViewModel = ViewModelProviders.of(this).get(RoomsViewModel.class);
        roomsViewModel.getAllItems().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(@NonNull List<Room> rooms) {
                adapter.setItems(rooms);
            }
        });

        ////////////Add item
        fbAddRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room room = new Room(R.drawable.bedroom, R.drawable.ic_edit1_black_24dp,
                        "اتاق جدید");
                roomsViewModel.insert(room);
            }
        });
        ///////delete Item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mLastDeletedRoom = adapter.getRoomAt(viewHolder.getAdapterPosition());
                roomsViewModel.delete(mLastDeletedRoom);
                showUndoSnackbar();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemNameClickListener(new RoomAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemTitleClick(Room room) {
                Intent intent = new Intent(RoomActivity.this, EditRoomsNameActivity.class);
                intent.putExtra(Constants.EXTRA_TITLE_ROOM, room.getNameItem());
                intent.putExtra(Constants.EXTRA_ID_ROOM, room.getId());
                startActivityForResult(intent, Constants.EDIT_TITLE_REQUEST_ROOM);
            }
        });

        adapter.setOnItemClickListener(new RoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(RoomActivity.this, DevicesActivity.class);
                intent.putExtra(Constants.PLACE_NAME, Constants.ROOM_ACTIVITY);
                intent.putExtra(Constants.PLACE_ID, room.getId());
                RoomActivity.this.startActivity(intent);
            }
        });
    }

    private void showUndoSnackbar() {
        View view = RoomActivity.this.findViewById(R.id.room_coordinator_layout);
        Snackbar snackbar = Snackbar.make(view, "اتاق مورد نظر پاک شد. ",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomActivity.this.undoDelete();
            }
        })
        .setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void undoDelete() {
        roomsViewModel.insert(mLastDeletedRoom);
    }

    ///////update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.EDIT_TITLE_REQUEST_ROOM && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Constants.EXTRA_ID_ROOM, -1);
            if (id == -1) {
                Toast.makeText(this, "Not cant be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(Constants.EXTRA_TITLE_ROOM);
            Room room = new Room(R.drawable.bedroom, R.drawable.ic_edit1_black_24dp, title);
            room.setId(id);
            roomsViewModel.update(room);
            Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    ///////////////////////////////////////////////////menu/////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                Intent intent = new Intent(this, AboutAsActivity.class);
                this.startActivity(intent);
                break;
        }
        return true;
    }
}
