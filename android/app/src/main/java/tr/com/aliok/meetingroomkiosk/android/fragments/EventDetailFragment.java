package tr.com.aliok.meetingroomkiosk.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tr.com.aliok.meetingroomkiosk.android.R;

/**
 * A fragment to show details of an event
 * Use the {@link EventDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailFragment extends Fragment {
    private static final String ARG_EVENT = "event";

    private Event event;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param event event to show details.
     * @return A new instance of fragment EventDetailFragment.
     */
    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment fragment = new EventDetailFragment();

        //TODO
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_EVENT, event);
//        fragment.setArguments(args);
        return fragment;
    }

    public EventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            // TODO
            // event = getArguments().getParcelable(ARG_EVENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
