package Model;

public class Chatdata {

    private String mDate;
    private String mId;
    private String mMessage;
    private String mName;

    public Chatdata(String mDate, String mId, String mMessage, String mName) {
        this.mDate = mDate;
        this.mId = mId;
        this.mMessage = mMessage;
        this.mName = mName;
    }

    public Chatdata() {
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String name) {
        mDate = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getId() {
        return mId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }


    public void setId(String id) {
        mId = id;
    }
}
