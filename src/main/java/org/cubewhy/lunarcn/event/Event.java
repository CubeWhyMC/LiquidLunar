package org.cubewhy.lunarcn.event;

import java.util.ArrayList;

import static org.cubewhy.lunarcn.utils.ClientUtils.*;

public class Event {
    /**
     * Call a event
     *
     * @return Event
     */
    public Event call() {
        final ArrayList<EventData> dataList = EventManager.get(this.getClass());

        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                EventData data = dataList.get(i);
                try {
                    data.target.invoke(data.source, this);
                } catch (Exception e) {
                    logger.catching(e);
                }
            }
        }
        return this;
    }
}
