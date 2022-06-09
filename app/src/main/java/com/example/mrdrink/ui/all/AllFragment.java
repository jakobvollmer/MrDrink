package com.example.mrdrink.ui.all;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mrdrink.ui.GameDataContainer;
import com.example.mrdrink.ui.filter.FilterFragment;
import com.example.mrdrink.ui.gameFragment.GameFragment;
import com.example.mrdrink.R;
import com.example.mrdrink.databinding.FragmentAllBinding;

import java.util.List;

public class AllFragment extends Fragment {

    private FragmentAllBinding binding;
    ListView gameListView;
    GamePreviewList gamePreviewList;

    private SwipeRefreshLayout swipeContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Lädt das ViewModel
        AllViewModel dashboardViewModel =
                new ViewModelProvider(this).get(AllViewModel.class);

        binding = FragmentAllBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Holt sich die Liste für die GamePreview
        gameListView = (ListView) root.findViewById(R.id.allGamesList);
        gameListView.setNestedScrollingEnabled(true);

        // Setzt einen Listener auf die Variable gameNameList und erstellt die GamePreviewList sobald die Variable sich ändert
        dashboardViewModel.getGameDataList().observe(getViewLifecycleOwner(), new Observer<List<GameDataContainer>>() {
            @Override
            public void onChanged(List<GameDataContainer> gamesList) {

                if (gamesList.size() != 0) {
                    gamePreviewList = new GamePreviewList(getActivity().getApplicationContext(), gamesList);
                    gameListView.setAdapter(gamePreviewList);
                    root.findViewById(R.id.loadingBar).setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }
                gamePreviewList.notifyDataSetChanged();
            }
        });

        // Setzt einen Listener auf die GamePreview List und lädt ein Game sobald ein Element ausgewählt wird
        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GameFragment gameFragment = new GameFragment();
                Bundle args = new Bundle();

                // Get ID of selected game
                int selectedGameID = gamePreviewList.gamesList.get(position).ID;
                args.putInt("gameID", selectedGameID);
                gameFragment.setArguments(args);

                // switch to GameFragment to show selected game
                ft.replace(getId(), gameFragment);
                ft.addToBackStack("AllFragment");
                ft.setReorderingAllowed(true);
                ft.commit();
            }
        });

        // Setzt einen Listener soballd von oben geswiped wird und lädt die Liste neu
        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardViewModel.requestGameList();
            }
        });
        /*
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        */

        //androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) ((MainActivity)getActivity()).findViewById(R.id.toolbar);

        //toolbar.setNavigationIcon(R.drawable.back_arrow);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setSearchString (String search)
    {
        Log.e("--", search);
        gamePreviewList.getFilter().filter(search);
    }

    public void setFilterView ()
    {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FilterFragment filterFragment = new FilterFragment();

        // switch to FilterFragment to show selected game
        ft.replace(getId(), filterFragment);
        ft.addToBackStack("FilterFragment");
        ft.setReorderingAllowed(true);
        ft.commit();
    }
}