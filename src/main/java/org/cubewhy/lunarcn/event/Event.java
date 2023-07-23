package org.cubewhy.lunarcn.event;

import java.util.ArrayList;

public class Event {
    /**
     * Call a event
     * @return Event
     */
    public Event callEvent() {
        final ArrayList<EventData> dataList = EventManager.get(this.getClass());

        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                EventData data = dataList.get(i);
                try {
                    data.target.invoke(data.source, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }
}
