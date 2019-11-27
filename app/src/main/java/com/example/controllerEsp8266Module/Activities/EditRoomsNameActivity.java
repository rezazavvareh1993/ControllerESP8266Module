package com.example.controllerEsp8266Module.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.controllerEsp8266Module.Constants;
import com.example.controllerEsp8266Module.R;

public class EditRoomsNameActivity extends AppCompatActivity {
    private EditText editTitle;
    private Button saveTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rooms_name);

        editTitle = findViewById(R.id.edit_title_rooms);
        saveTitle = findViewById(R.id.save_title_rooms);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_ID_ROOM)) {
            editTitle.setText(intent.getStringExtra(Constants.EXTRA_TITLE_ROOM));
        }
        saveTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
            }
        });

    }

    private void saveName() {
        String newName = editTitle.getText().toString();

        if (newName.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();

        data.putExtra(Constants.EXTRA_TITLE_ROOM, newName);

        int id = getIntent().getIntExtra(Constants.EXTRA_ID_ROOM, -1);
        if (id != -1) {
            data.putExtra(Constants.EXTRA_ID_ROOM, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
