package org.cubewhy.lunarcn.event;

public class EventCancelable extends Event {

    private boolean canceled = false;

    /**
     * Get cancel state
     *
     * @return state
     * */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Do cancel event
     *
     * @return self
     */
    public EventCancelable cancel() {
        canceled = true;
        return this;
    }

    /**
     * Call the event
     *
     * @return self
     * */
    @Override
    public EventCancelable call() {
        return (EventCancelable) super.call();
    }
}
