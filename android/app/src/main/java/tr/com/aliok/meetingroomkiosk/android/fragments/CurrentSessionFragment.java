package tr.com.aliok.meetingroomkiosk.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import tr.com.aliok.meetingroomkiosk.android.R;
import tr.com.aliok.meetingroomkiosk.android.components.CountDownTextView;
import tr.com.aliok.meetingroomkiosk.android.model.Event;

/**
 * Fragment for displaying current session tab. It includes detail for current session (or next session)
 * and list of today's upcoming sessions.
 * <p/>
 * Activities that contain this fragment must implement the
 * {@link tr.com.aliok.meetingroomkiosk.android.fragments.CurrentSessionFragment.ActivityContract} interface
 * to handle interaction events.
 * Use the {@link CurrentSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentSessionFragment extends Fragment implements CountDownTextView.CountDownListener {

    private ActivityContract activityContract;

    private CountDownTextView mCountDownTextView;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment CurrentSessionFragmet.
     */
    public static CurrentSessionFragment newInstance() {
        return new CurrentSessionFragment();
    }

    public CurrentSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_current_session, container, false);

        this.mCountDownTextView = (CountDownTextView) view.findViewById(R.id.countDownTextView);
        this.mCountDownTextView.setCountDownListener(this);

        doRefreshViewWithDataFromActivity();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityContract = (ActivityContract) activity;
            activityContract.setCurrentSessionFragment(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ActivityContract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityContract.setCurrentSessionFragment(null);
        activityContract = null;
    }

    public void dataArrived() {
        doRefreshViewWithDataFromActivity();
    }

    private void doRefreshViewWithDataFromActivity() {
        final Event currentEvent = activityContract.getCurrentEvent();
        final List<Event> upcomingEvents = activityContract.getUpcomingEvents();

        // perhaps the fragment is created but the data request of activity is not done yet!
        // so, check if activity received the data
        if (currentEvent == null) {
            // TODO: display "no event"
            if (CollectionUtils.isEmpty(upcomingEvents)) {
                // TODO: display "no upcoming event"
            } else {
                final Event nextEvent = upcomingEvents.get(0);      // they're already sorted, get the first one
                // TODO: display next event
                // start countdown for start of next event
                this.mCountDownTextView.startCountDown(nextEvent.getEventStart());
            }
        } else {
            // TODO display current event details
            // start count down for end of current event
            this.mCountDownTextView.startCountDown(currentEvent.getEventEnd());
        }

        if (CollectionUtils.isEmpty(upcomingEvents)) {
            // TODO display "no upcoming events"
        } else {
            // TODO display list of upcoming events
        }

        // TODO refresh view when countdown is finished
        // TODO best way : creating a countdown component which notifies parent when countdown == 00:00:00
    }

    @Override
    public void onCountDownFinished(CountDownTextView countDownTextView) {
        activityContract.refresh();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     */
    public interface ActivityContract {
        public void setCurrentSessionFragment(CurrentSessionFragment currentSessionFragment);

        void onEventSelected(Event event);

        Event getCurrentEvent();

        List<Event> getUpcomingEvents();

        void refresh();
    }

}
