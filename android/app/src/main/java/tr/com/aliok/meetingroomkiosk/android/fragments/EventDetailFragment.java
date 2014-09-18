package tr.com.aliok.meetingroomkiosk.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.cengalabs.flatui.views.FlatTextView;

import tr.com.aliok.meetingroomkiosk.android.R;
import tr.com.aliok.meetingroomkiosk.android.model.Event;

/**
 * A fragment to show details of an event
 * Use the {@link EventDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailFragment extends Fragment {
    private static final String ARG_EVENT = "event";

    private Event event;

    // ------ component bindings -----------------
    private FlatTextView mTimeRangeTextView;
    private FlatTextView mTitleTextView;
    private FlatTextView mDescriptionTextView;
    private FlatTextView mOrganizerTextView;
    private GridLayout mAttendeesGridLayout;


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
        final View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        // this is the moment to bind components, not before
        bindComponents(view);

        return view;
    }

    private void bindComponents(View view) {
        this.mTimeRangeTextView = (FlatTextView) view.findViewById(R.id.timeRangeTextView);
        this.mTitleTextView = (FlatTextView) view.findViewById(R.id.titleTextView);
        this.mDescriptionTextView = (FlatTextView) view.findViewById(R.id.descriptionTextView);
        this.mOrganizerTextView = (FlatTextView) view.findViewById(R.id.organizerLabelTextView);
        this.mAttendeesGridLayout = (GridLayout) view.findViewById(R.id.attendeesGridLayout);
    }

    public void updateFragmentWithEvent(Event event) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
