package com.example.mrdrink.ui.all;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrdrink.R;
import com.example.mrdrink.ui.GameDataContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllViewModel extends ViewModel {

    private final MutableLiveData<List<GameDataContainer>> gameDataList;
    List<GameDataContainer> buff = new ArrayList<GameDataContainer>();
    private int x = 0;

    public AllViewModel() {
        gameDataList = new MutableLiveData<>();
        requestGameList();
    }

    public LiveData<List<GameDataContainer>> getGameDataList () {
        return gameDataList;
    }

    public void requestGameList ()
    {
        x++;

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buff.clear();

                buff.add(new GameDataContainer(0, "A Game: " + String.valueOf(0), "outdoor_tag", "group_tag", "indoor_tag"));
                buff.add(new GameDataContainer(1, "B Game: " + String.valueOf(1), "outdoor_tag", "group_tag", "indoor_tag"));
                buff.add(new GameDataContainer(2, "B Game: " + String.valueOf(2), "outdoor_tag", "group_tag", "indoor_tag"));
                buff.add(new GameDataContainer(3, "C Game: " + String.valueOf(3), "outdoor_tag", "group_tag", "indoor_tag"));
                buff.add(new GameDataContainer(4, "C Game: " + String.valueOf(4), "outdoor_tag", "group_tag", "indoor_tag"));

                if (x == 2){
                    buff.clear();
                    for (int x=0; x<15; x++)
                    {
                        buff.add(new GameDataContainer(x, "Game: " + String.valueOf(x), "outdoor_tag", "group_tag", "indoor_tag"));
                    }
                }
                gameDataList.setValue(buff);
            }
        }, 2000);
    }
}