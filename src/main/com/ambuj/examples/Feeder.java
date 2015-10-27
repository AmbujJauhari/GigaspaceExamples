package main.com.ambuj.examples;

import com.j_spaces.core.IJSpace;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Feeder {


    public static void main(String[] args) {
        String url = "jini://*/*/processorSpace";
        GigaSpace space = getGigaSpace(url);
        Person p1 = new Person();
        p1.setName("Ambuj");
        p1.setAge(27);
        p1.setJob("TA");
        p1.setRoutingId(p1.getName());

        Person p2 = new Person();
        p2.setName("Mark");
        p2.setAge(27);
        p2.setJob("TL");
        p2.setRoutingId(p1.getName());

        space.write(p1);
        space.write(p2);
    }

    public static GigaSpace getGigaSpace(String url) {
        IJSpace space = new UrlSpaceConfigurer(url).space();

        GigaSpace gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();
        return gigaSpace;
    }
}
