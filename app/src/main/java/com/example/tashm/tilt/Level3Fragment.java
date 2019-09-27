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
public class Level3Fragment extends Fragment {

    public static String ARG_PAGE = "ARG_PAGE";
    ImageView levelSelector;
    private int page;

    public Level3Fragment() {
        // Required empty public constructor
    }

    public static Level3Fragment create(int page, String ARG_PAGE){
        Level3Fragment gameFragment = new Level3Fragment();
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
        View v = inflater.inflate(R.layout.fragment_level3, container, false);
        levelSelector = (ImageView) v.findViewById(R.id.level3Selector);
        levelSelector.setImageResource(R.drawable.level_3);
        return v;
    }

}