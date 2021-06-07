package uk.org.websolution.geeknotepad;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteEntity implements Parcelable {
    String title;
    String text;
    String date;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public NoteEntity(String title, String text, String date) {
        this.title = title;
        this.text = text;
        this.date = date;
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
