package viewitdoit.anchorsmedia.com.viewitdoit.event;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import viewitdoit.anchorsmedia.com.viewitdoit.R;
import viewitdoit.anchorsmedia.com.viewitdoit.ViewItDoIt;
import viewitdoit.anchorsmedia.com.viewitdoit.utils.BitmapLruCache;
import viewitdoit.anchorsmedia.com.viewitdoit.utils.VolleySingleton;

public class EventFragment extends android.support.v4.app.Fragment {

    public static final String EVENT_KEY = "event_key";
    private Event mEvent;
    private ImageLoader mImageLoader;
    private ImageView imageView;
    private TextView textTitle;
    private TextView textBody;
    private TextView textCountdown;
    private FragmentListener mListener;

    public EventFragment(){
        // Required empty public constructor.
    }

    public static EventFragment newInstance(Event event) {

        Bundle args = new Bundle();
        args.putParcelable(EVENT_KEY, event);
        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof FragmentListener)) throw new AssertionError();
        mListener = (FragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event_detail, container, false);

        Event event = getArguments().getParcelable(EVENT_KEY);

        if(event.getLargeImageUrl() != null){
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            ImageLoader imageLoader = new ImageLoader(VolleySingleton.getInstance(ViewItDoIt.getAppContext()).getRequestQueue(), imageCache);
            NetworkImageView imageView = (NetworkImageView) rootView.findViewById(R.id.imageView);
            imageView.setImageUrl(event.getLargeImageUrl(), imageLoader);
        }

        textTitle = (TextView) rootView.findViewById(R.id.textTitle);
        Typeface lato_black = Typeface.createFromAsset(getContext().getAssets(), "fonts/lato/lato_black.ttf");
        textTitle.setTypeface(lato_black);
        textTitle.setText(event.getTitle());

        textBody = (TextView) rootView.findViewById(R.id.textDescription);
        Typeface lato_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/lato/lato_bold.ttf");
        textTitle.setTypeface(lato_bold);
        textBody.setText(event.getShortBody());

        textCountdown = (TextView) rootView.findViewById(R.id.textCountdown);
        textCountdown.setText("Starts " + event.getStartDateTime());

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        return rootView;
    }

    private void done(){
        if(mListener == null){
            throw new AssertionError();
        }

    }

    public interface FragmentListener {
        void onFragmentFinish(Event event);
    }

}
