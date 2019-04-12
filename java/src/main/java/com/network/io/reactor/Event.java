package com.network.io.reactor;

public class Event {
    private InputSource source;
    private EventType type;

    InputSource getSource() {
        return source;
    }

    void setSource(InputSource source) {
        this.source = source;
    }

    EventType getType() {
        return type;
    }

    void setType(EventType type) {
        this.type = type;
    }
}
