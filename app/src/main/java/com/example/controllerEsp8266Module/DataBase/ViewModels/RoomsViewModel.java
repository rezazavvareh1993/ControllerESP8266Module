package com.example.controllerEsp8266Module.DataBase.ViewModels;

import android.app.Application;

import com.example.controllerEsp8266Module.DataBase.Repositories.RoomsRepository;
import com.example.controllerEsp8266Module.DataBase.Tables.Room;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RoomsViewModel extends AndroidViewModel {
    private RoomsRepository repository;
    private LiveData<List<Room>> allItems;

    public RoomsViewModel(@NonNull Application application) {
        super(application);

        repository = new RoomsRepository(application);
        allItems = repository.getAllItems();

    }

    public void insert(Room room) {
        repository.insert(room);
    }

    public void update(Room room) {
        repository.update(room);
    }

    public void delete(Room room) {
        repository.delete(room);
    }

    public LiveData<List<Room>> getAllItems() {
        return allItems;
    }

}
