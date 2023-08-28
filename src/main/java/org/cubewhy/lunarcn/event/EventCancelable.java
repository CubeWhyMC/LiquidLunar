package org.cubewhy.lunarcn.event;

public class EventCancelable extends Event {

    private boolean canceled = false;

    public boolean isCanceled() {
        return canceled;
    }

    public EventCancelable cancel() {
        canceled = true;
        return this;
    }

    @Override
    public EventCancelable call() {
        return (EventCancelable) super.call();
    }
}
