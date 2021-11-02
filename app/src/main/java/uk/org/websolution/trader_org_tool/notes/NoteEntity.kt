package uk.org.websolution.trader_org_tool.notes

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

class NoteEntity : Parcelable {
    var title: String? = null
    var text: String? = null
    var date: String? = null
        private set
    private var id: String? = null

    constructor() {}

    fun getId(): String {
        return id.toString()
    }

    constructor(title: String?, text: String?) {
        this.title = title
        this.text = text
        date = Calendar.getInstance().time.toString()
        id = UUID.randomUUID().toString()
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        text = `in`.readString()
        date = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(text)
        dest.writeString(date)
    }

    companion object {
        @JvmField val CREATOR: Creator<NoteEntity?> = object : Creator<NoteEntity?> {
            override fun createFromParcel(`in`: Parcel): NoteEntity? {
                return NoteEntity(`in`)
            }

            override fun newArray(size: Int): Array<NoteEntity?> {
                return arrayOfNulls(size)
            }
        }
    }
}