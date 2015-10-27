package main.com.ambuj.examples;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.springframework.transaction.annotation.Transactional;

@EventDriven
@Transactional
@Notify(gigaSpace = "gigaSpace", performTakeOnNotify = true)
public class NotifyContainer {

    @GigaSpaceContext
    private GigaSpace gigaSpace;

    @EventTemplate
    public SQLQuery<Employee> template() {
        SQLQuery<Employee> template = new SQLQuery<>(Employee.class, "name = ?");
        template.setParameter(1, "Anuj");
        return template;
    }

    @SpaceDataEvent
    public void eventListener(Employee event) {
        System.out.println("Consumed the event : " + event);
        event.setName("Jonnah");
        System.out.println("Updated the event with new values" + event);
    }
}
