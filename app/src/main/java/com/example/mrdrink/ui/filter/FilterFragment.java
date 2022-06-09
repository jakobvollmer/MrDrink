package com.example.mrdrink.ui.filter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mrdrink.R;
import com.example.mrdrink.databinding.FragmentFilterBinding;

import java.util.List;

public class FilterFragment extends Fragment {

    private FragmentFilterBinding binding;

    ListView filterListView;

    FilterList filterList;

    public FilterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static com.example.mrdrink.ui.gameFragment.GameFragment newInstance(String param1, String param2) {
        com.example.mrdrink.ui.gameFragment.GameFragment fragment = new com.example.mrdrink.ui.gameFragment.GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Fügt den Pfeil zurück in die Toolbar hinzu
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        // Lädt das ViewModel vom Game Fragment
        FilterViewModel filterViewModel =
                new ViewModelProvider(this).get(FilterViewModel.class);

        filterListView = (ListView) root.findViewById(R.id.filter_list);

        filterViewModel.getFiltersList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> filtersList) {
                Log.e("---", "___");
                if (filtersList.size() != 0) {

                    filterList = new FilterList(getActivity().getApplicationContext(), filtersList);
                    filterListView.setAdapter(filterList);
                    root.findViewById(R.id.loadingBarFilterFragment).setVisibility(View.GONE);
                }

                filterList.notifyDataSetChanged();
            }
        });

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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }
}