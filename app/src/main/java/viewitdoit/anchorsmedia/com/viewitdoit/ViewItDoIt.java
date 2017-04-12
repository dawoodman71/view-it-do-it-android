package viewitdoit.anchorsmedia.com.viewitdoit;

import android.app.Application;
import android.content.Context;

public class ViewItDoIt extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ViewItDoIt.context = getApplicationContext();
    }

    public static Context getAppContext() { return ViewItDoIt.context; }
}
