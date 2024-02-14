package main.EventListeners.Moderation;

public enum LeaveType {

    BANNED("wurde von einem Moderator gebannt "),
    KICKED("wurde von einem Moderator entfernt "),
    LEFT("hat den Server verlassen");

    private final String text;

    LeaveType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
    }
