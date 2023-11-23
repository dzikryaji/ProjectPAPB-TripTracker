package das.mobile.triptracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import das.mobile.triptracker.R;
import das.mobile.triptracker.databinding.FragmentOnBoarding2Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnBoarding2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnBoarding2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OnBoarding2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnBoarding2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnBoarding2Fragment newInstance(String param1, String param2) {
        OnBoarding2Fragment fragment = new OnBoarding2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentOnBoarding2Binding binding = FragmentOnBoarding2Binding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager2 viewPager = getActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(2, true);
            }
        });

        return binding.getRoot();
    }
}