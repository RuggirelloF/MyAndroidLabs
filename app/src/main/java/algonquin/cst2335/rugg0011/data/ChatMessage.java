package algonquin.cst2335.rugg0011.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @ColumnInfo(name = "message")
    protected String message;

    @ColumnInfo(name = "sendOrReceive")
    protected boolean isSentButton;

    @ColumnInfo(name = "timeSent")
    protected String timeSent;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    public ChatMessage(){

    }

    public ChatMessage(String message, String timeSent, boolean isSentButton){
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean getIsSentButton() {
        return isSentButton;
    }
}
