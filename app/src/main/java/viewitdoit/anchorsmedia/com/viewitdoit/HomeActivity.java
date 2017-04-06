package viewitdoit.anchorsmedia.com.viewitdoit;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    public final String TAG = "HomeActivity";

    private MediaPlayer mp = null;
    SurfaceView mSurfaceView = null;

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
}
