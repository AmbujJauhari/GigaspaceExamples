package main.com.ambuj.examples;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;
import org.springframework.transaction.annotation.Transactional;

@EventDriven
@Transactional
@Polling(gigaSpace = "gigaSpace", concurrentConsumers = 1, maxConcurrentConsumers = 1, receiveTimeout = 10000)
public class PersonPollingContainer {

    @GigaSpaceContext
    private GigaSpace gigaSpace;

    @EventTemplate
    public SQLQuery<Person> template() {
        SQLQuery<Person> template = new SQLQuery<>(Person.class, "name = ?");
        template.setParameter(1, "Ambuj");
        return template;
    }

    @ReceiveHandler
    public ReceiveOperationHandler recieveHandler() {
        SingleTakeReceiveOperationHandler singleTakeReceiveOperationHandler = new SingleTakeReceiveOperationHandler();
        return singleTakeReceiveOperationHandler;
    }

    @SpaceDataEvent
    public Person eventListener(Person event) {
        System.out.println("Consumed the event : " + event);
        event.setName("Jonnah");
        System.out.println("Updated the event with new values" + event);
        return event;
    }
}
