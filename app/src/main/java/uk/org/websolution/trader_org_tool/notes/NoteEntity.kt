package uk.org.websolution.trader_org_tool.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

import uk.org.websolution.trader_org_tool.R;

public class NoteEntity implements Parcelable {
    private String title;
    private String text;
    private String date;
    private String id;

    public NoteEntity() {

    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getId() {
        return String.valueOf(id);
    }


    public NoteEntity(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = Calendar.getInstance().getTime().toString();
        id = UUID.randomUUID().toString();
    }


    protected NoteEntity(Parcel in) {
        title = in.readString();
        text = in.readString();
        date = in.readString();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(date);
    }
}
