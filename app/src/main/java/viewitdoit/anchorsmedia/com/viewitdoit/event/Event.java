package viewitdoit.anchorsmedia.com.viewitdoit.event;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Event implements Parcelable {
    private int eventId;
    private String startDateTime;
    private String endDateTime;
    private String imageFilename;
    private String title;
    private String shortBody;
    private String body;
    private String videoUrl;
    private String dataUrl;

    public Event() {

    }

    public Event(JSONObject data) {
        this.eventId = data.optInt("id");
        this.startDateTime = data.optString("start_datetime");
        this.endDateTime = data.optString("end_datetime");
        this.imageFilename = data.optString("avatar_file_name");
        this.title = data.optString("title");
        this.shortBody = data.optString("shortbody");
        this.body = data.optString("body");
        this.videoUrl = data.optString("ios_url_path");
        this.dataUrl = data.optString("url");
    }

    public int getEventId() {
        return eventId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getSmallImageUrl() {
        String idString = String.format("%03d", eventId);
        return String.format("https://viewitdoit.com/system/live_events/avatars/000/000/%s/medium/%s", idString, imageFilename);
    }

    public String getLargeImageUrl() {
        String idString = String.format("%03d", eventId);
        return String.format("https://viewitdoit.com/system/live_events/avatars/000/000/%s/large/%s", idString, imageFilename);
    }

    public String getShortBody() {
        return shortBody;
    }

    public String getBody() {
        return body;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.eventId);
        dest.writeString(this.startDateTime);
        dest.writeString(this.endDateTime);
        dest.writeString(this.imageFilename);
        dest.writeString(this.title);
        dest.writeString(this.shortBody);
        dest.writeString(this.body);
        dest.writeString(this.videoUrl);
        dest.writeString(this.dataUrl);
    }

    protected Event(Parcel in) {
        this.eventId = in.readInt();
        this.startDateTime = in.readString();
        this.endDateTime = in.readString();
        this.imageFilename = in.readString();
        this.title = in.readString();
        this.shortBody = in.readString();
        this.body = in.readString();
        this.videoUrl = in.readString();
        this.dataUrl = in.readString();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
