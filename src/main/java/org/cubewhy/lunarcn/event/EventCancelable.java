package org.cubewhy.lunarcn.event;

public class EventCancelable extends Event {

    private boolean canCalled = false;

    public boolean isCanCalled() {
        return canCalled;
    }

    public void setCanCalled(boolean value) {
        canCalled = value;
    }
}
