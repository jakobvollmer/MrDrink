package com.example.mrdrink.ui.all;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrdrink.R;
import com.example.mrdrink.ui.GameDataContainer;

import java.util.ArrayList;
import java.util.List;

public class GamePreviewList extends BaseAdapter implements Filterable {
    Context context;
    public List<GameDataContainer> gamesList;
    public List<GameDataContainer> gamesFilterList;

    LayoutInflater inflter;

    private ValueFilter valueFilter;

    public GamePreviewList(Context applicationContext, List<GameDataContainer> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
        this.gamesFilterList = gamesList;
        inflter = (LayoutInflater.from(applicationContext));
        getFilter();
    }

    @Override
    public int getCount() {
        return gamesList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Lädt ein Listenelement

        GameDataContainer gameData = this.gamesList.get(i);
        Context c = viewGroup.getContext();

        view = inflter.inflate(R.layout.game_preview_item, null);

        TextView gameName = (TextView) view.findViewById(R.id.gameName);
        gameName.setText(gameData.name);

        int idTag1 = c.getResources().getIdentifier("drawable/" + gameData.tag1, null, c.getPackageName());
        ImageView tag1 = (ImageView) view.findViewById(R.id.tag1);
        tag1.setImageResource(idTag1);

        int idTag2 = c.getResources().getIdentifier("drawable/" + gameData.tag2, null, c.getPackageName());
        ImageView tag2 = (ImageView) view.findViewById(R.id.tag2);
        tag2.setImageResource(idTag2);

        int idTag3 = c.getResources().getIdentifier("drawable/" + gameData.tag3, null, c.getPackageName());
        ImageView tag3 = (ImageView) view.findViewById(R.id.tag3);
        tag3.setImageResource(idTag3);

        return view;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                List<GameDataContainer> filterList = new ArrayList<GameDataContainer>();

                for (int i=0; i < gamesFilterList.size(); i++) {
                    if(gamesFilterList.get(i).getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        filterList.add(gamesFilterList.get(i));
                        Log.e("##--", gamesFilterList.get(i).name);
                    }
                }

                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=gamesFilterList.size();
                results.values=gamesFilterList;
            }
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            gamesList=(List<GameDataContainer>) results.values;
            notifyDataSetChanged();
        }
    }
}
