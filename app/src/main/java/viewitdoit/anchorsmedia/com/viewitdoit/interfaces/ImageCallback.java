package viewitdoit.anchorsmedia.com.viewitdoit.interfaces;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;

public interface ImageCallback {
    void onSuccess(Bitmap bitmap);

    void onError(VolleyError volleyError);
}