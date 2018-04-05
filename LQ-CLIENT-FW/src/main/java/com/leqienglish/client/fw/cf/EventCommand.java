package com.leqienglish.client.fw.cf;

import javafx.event.Event;

/**
 *
 * @author duyi
 */
public abstract class EventCommand extends Command {

    /**
     * 当前事件的Event
     */
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
