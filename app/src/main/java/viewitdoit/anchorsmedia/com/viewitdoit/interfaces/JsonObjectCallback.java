package viewitdoit.anchorsmedia.com.viewitdoit.interfaces;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public interface JsonObjectCallback extends Response.ErrorListener {
    void onSuccess(JSONObject result) throws JSONException;

    void onError(VolleyError volleyError);

    @Override
    void onErrorResponse(VolleyError error);
}
