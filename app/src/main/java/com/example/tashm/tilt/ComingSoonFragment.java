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
public class ComingSoonFragment extends Fragment {

    public static String ARG_PAGE = "ARG_PAGE";
    ImageView levelSelector;
    private int page;

    public ComingSoonFragment() {
        // Required empty public constructor
    }

    //creates the fragment when it gets called inside a viewpager
    public static ComingSoonFragment create(int page, String ARG_PAGE){
        ComingSoonFragment gameFragment = new ComingSoonFragment();
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
        View v = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        //sets the image of the fragment to the image of the level, atm, this shows
        //locked levels because i only have 3 levels and if the person has not completed the previous
        //level, then it will display this fragment.
        levelSelector = (ImageView) v.findViewById(R.id.comingSoon);
        levelSelector.setImageResource(R.drawable.ic_lock_black_48dp);
        return v;
    }

}

