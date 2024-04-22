package com.disruption.EventListeners.Moderation;

public enum LeaveType {

    BANNED("wurde von einem Moderator gebannt "),
    KICKED("wurde von einem Moderator entfernt "),

    TIMEOUTED(" in den timeout versetzt. ");

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
