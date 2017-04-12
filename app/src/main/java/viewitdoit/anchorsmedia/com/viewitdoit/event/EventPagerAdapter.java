package viewitdoit.anchorsmedia.com.viewitdoit.event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EventPagerAdapter extends FragmentStatePagerAdapter {

    private int mLayout;
    private JSONArray mEvents;
    private int mNum;

    public EventPagerAdapter(FragmentManager fm, int layout, JSONArray events) {
        super(fm);
        mLayout = layout;
        mEvents = events;
        mNum = mEvents.length();
    }

    @Override
    public Fragment getItem(int position) {
        JSONObject eventObject = null;
        EventFragment fragment = null;

        try {
            eventObject = mEvents.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(eventObject == null){
            return null;
        } else {
            fragment = EventFragment.newInstance(new Event(eventObject));
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNum;
    }
}
