package com.example.controllerEsp8266Module.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllerEsp8266Module.Adapters.PlaceHouseAdapter;
import com.example.controllerEsp8266Module.Constants;
import com.example.controllerEsp8266Module.Models.PlaceHouseModel;
import com.example.controllerEsp8266Module.R;

public class SmartHouseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
//    public final String SMART_HOUSE_ACTIVITY_NAME = "smartHouseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_house);

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        PlaceHouseAdapter adapter = new PlaceHouseAdapter(this, PlaceHouseModel.getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new PlaceHouseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlaceHouseModel placeHouseModel, int position) {
                if (placeHouseModel.getTxtPlace().equals("اتاق خواب")) {
                    Intent intent = new Intent(SmartHouseActivity.this, RoomActivity.class);
                    SmartHouseActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SmartHouseActivity.this, DevicesActivity.class);
                    intent.putExtra(Constants.PLACE_NAME, placeHouseModel.getTxtPlace());
                    intent.putExtra(Constants.PLACE_ID, position);
//              intent.putExtra(Constants.ACTIVITY_NAME, SMART_HOUSE_ACTIVITY_NAME);
                    SmartHouseActivity.this.startActivity(intent);
                    Toast.makeText(SmartHouseActivity.this, "وسایل مربوط به " + placeHouseModel.getTxtPlace(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    ///////////////////////////////////////////////////menu/////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, getResources().getString(R.string.about_us));
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
