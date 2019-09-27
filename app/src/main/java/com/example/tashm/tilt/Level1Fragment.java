package com.example.tashm.tilt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Level1Fragment extends Fragment {

    public static String ARG_PAGE = "ARG_PAGE";
    ImageView levelSelector;
    private int page;

    public Level1Fragment() {
        // Required empty public constructor
    }

    //creates the level 1 fragment
    public static Level1Fragment create(int page, String ARG_PAGE){
        Level1Fragment gameFragment = new Level1Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAGE, page);
        bundle.putString("ARG_PAGE", ARG_PAGE);
        gameFragment.setArguments(bundle);
        return gameFragment;
    }

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        page = getArguments().getInt(ARG_PAGE, 0);
        ARG_PAGE = getArguments().getString("ARG_PAGE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_level1, container, false);
        //imageview to retrieve the drawable xml file of the first level
        levelSelector = (ImageView) v.findViewById(R.id.level1Selector);
        levelSelector.setImageResource(R.drawable.level_1);
        return v;
    }

}
