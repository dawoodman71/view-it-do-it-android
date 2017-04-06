package viewitdoit.anchorsmedia.com.viewitdoit.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

public interface JsonArrayCallback {
    void onSuccess(JSONArray result) throws JSONException;

    void onError(VolleyError volleyError);
}