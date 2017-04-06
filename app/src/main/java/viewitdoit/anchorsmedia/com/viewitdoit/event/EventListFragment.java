package viewitdoit.anchorsmedia.com.viewitdoit.event;

import android.app.ListFragment;

public class EventListFragment extends ListFragment {
    int mNum;

    static EventListFragment newInstance(int num){
        EventListFragment f = new EventListFragment();
        return f;
    }
}
