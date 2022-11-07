package algonquin.cst2335.rugg0011.data;

public class ChatMessage {

    String message;
    String timeSent;
    boolean isSentButton;

    public void ChatRoom(String message, String timeSent, boolean isSentButton){
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
