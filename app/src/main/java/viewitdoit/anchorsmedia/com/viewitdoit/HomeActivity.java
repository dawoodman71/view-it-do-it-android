package viewitdoit.anchorsmedia.com.viewitdoit;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import viewitdoit.anchorsmedia.com.viewitdoit.event.Event;
import viewitdoit.anchorsmedia.com.viewitdoit.event.EventFragment;
import viewitdoit.anchorsmedia.com.viewitdoit.event.EventPagerAdapter;
import viewitdoit.anchorsmedia.com.viewitdoit.utils.DateUtil;
import viewitdoit.anchorsmedia.com.viewitdoit.utils.VolleySingleton;

public class HomeActivity extends AppCompatActivity implements SurfaceHolder.Callback, EventFragment.FragmentListener {

    private static final String TAG = "HomeActivity";
    private List<Event> events = null;

    private MediaPlayer mp = null;
    SurfaceView mSurfaceView = null;
    ViewPager mViewPager = null;
    PagerAdapter mViewPagerAdapter = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_events:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mp = new MediaPlayer();
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceView.getHolder().addCallback(this);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);


        loadJsonFeed();
    }

    private void loadJsonFeed() {
        Log.i(TAG, "loadJsonFeed");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "https://www.viewitdoit.com/live_events.json", null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        final JSONArray upcoming = new JSONArray();
                        Date now = new Date();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject eventObject = response.getJSONObject(i);
                                if(DateUtil.isUpcoming(eventObject.getString("start_datetime"))){
                                    upcoming.put(eventObject);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mViewPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), R.layout.fragment_event_preview, upcoming);
                        mViewPager.setAdapter(mViewPagerAdapter);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: " + error.toString());
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.background);
        try {
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mp.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        int videoWidth = mp.getVideoWidth();
        int videoHeight = mp.getVideoHeight();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
        lp.width = screenWidth;
        lp.height = (int) (((float) videoHeight / (float) videoWidth) * (float) screenWidth);

        mSurfaceView.setLayoutParams(lp);

        mp.setDisplay(holder);
        mp.setLooping(true);
        mp.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onFragmentFinish(Event event) {
        
    }
}
