package com.example.mrdrink.ui.gameFragment;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrdrink.ui.GameDataContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameViewModel extends ViewModel {

    private final MutableLiveData<GameDataContainer> gameData;

    public GameViewModel() {
        this.gameData = new MutableLiveData<>();
    }

    public LiveData<GameDataContainer> getGame () {
        return this.gameData;
    }

    public void requestGameData (int gameID)
    {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameDataContainer gameDataBuff = new GameDataContainer(gameID, "TEST LOAD", "outdoor_tag", "group_tag", "indoor_tag");
                gameDataBuff.explanation = "Dies ist eine Test Erklärung";
                gameDataBuff.neededMaterials = Arrays.asList("1. Sache x", "2. Sache y", "3. Sache z");

                gameData.setValue(gameDataBuff);
            }
        }, 1000);
    }
}