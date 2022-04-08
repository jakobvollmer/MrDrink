package com.example.mrdrink.ui.gameFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mrdrink.R;
import com.example.mrdrink.databinding.FragmentAllBinding;
import com.example.mrdrink.ui.GameDataContainer;
import com.example.mrdrink.ui.all.AllFragment;
import com.example.mrdrink.ui.all.GamePreviewList;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    private FragmentAllBinding binding;

    public GameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAllBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Fügt den Pfeil zurück in die Toolbar hinzu
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        // Lädt das ViewModel vom Game Fragment
        GameViewModel gameViewModel =
                new ViewModelProvider(this).get(GameViewModel.class);

        // Setzt einen Listener auf den zurück Pfeil und leitet das Event auf die zurück Taste weiter
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        // Setzt einen Listener auf die zurück Taste und lädt das navigation_all Fragment
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setNavigationIcon(R.drawable.ic_launcher);
                toolbar.setNavigationOnClickListener(null);

                FragmentManager fm = getParentFragmentManager();
                fm.popBackStack();
            }
        });

        // Get selected gameID
        Bundle args = getArguments();
        int gameID = args.getInt("gameID", 0);

        // Setzt einen Listener auf die Variable gameNameList und erstellt die GamePreviewList sobald die Variable sich ändert
        gameViewModel.getGame().observe(getViewLifecycleOwner(), new Observer<GameDataContainer>() {
            @Override
            public void onChanged(GameDataContainer gameData) {
                // Set game name/title
                TextView gameTitle = (TextView) getView().findViewById(R.id.gameName);
                gameTitle.setText(gameData.name);

                // Set explanation text
                TextView explanation = (TextView) getView().findViewById(R.id.explanationTextView);
                explanation.setText(gameData.explanation);

                // Set needed materials list
                ListView neededMaterialList = (ListView) getView().findViewById(R.id.neededMaterialsList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( getActivity(),
                        R.layout.needed_material_item,
                        gameData.neededMaterials );
                neededMaterialList.setAdapter(arrayAdapter);
                getView().findViewById(R.id.loadingBarGameFragment).setVisibility(View.GONE);

                // Set tag icons
                Context c = getView().getContext();

                int idTag1 = c.getResources().getIdentifier("drawable/" + gameData.tag1, null, c.getPackageName());
                ImageView tag1 = (ImageView) getView().findViewById(R.id.gameTag1);
                tag1.setImageResource(idTag1);

                int idTag2 = c.getResources().getIdentifier("drawable/" + gameData.tag2, null, c.getPackageName());
                ImageView tag2 = (ImageView) getView().findViewById(R.id.gameTag2);
                tag2.setImageResource(idTag2);

                int idTag3 = c.getResources().getIdentifier("drawable/" + gameData.tag3, null, c.getPackageName());
                ImageView tag3 = (ImageView) getView().findViewById(R.id.gameTag3);
                tag3.setImageResource(idTag3);
            }
        });

        gameViewModel.requestGameData(gameID);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }
}