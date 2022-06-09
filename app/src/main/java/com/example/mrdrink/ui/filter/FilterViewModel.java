package com.example.mrdrink.ui.filter;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterViewModel extends ViewModel {

    private final MutableLiveData<List<String>> filtersList;

    public FilterViewModel() {
        filtersList = new MutableLiveData<>();
        requestFiltersList();
    }

    public LiveData<List<String>> getFiltersList () {
        return filtersList;
    }

    public void requestFiltersList ()
    {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> buffList = new ArrayList<>();
                buffList.clear();

                buffList.add("Filter 1");
                buffList.add("Filter 2");
                buffList.add("Filter 3");
                buffList.add("Filter 4");

                filtersList.setValue(buffList);
            }
        }, 100);
    }
}