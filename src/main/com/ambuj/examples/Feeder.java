package main.com.ambuj.examples;

import com.gigaspaces.async.AsyncFuture;
import com.j_spaces.core.IJSpace;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class Feeder {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        Employee e1 = new Employee();
        e1.setSpaceId("1");
        e1.setName("Anuj");
        e1.setDepartment("Accounting");
        e1.setSalary(40000);

        Employee e2 = new Employee();
        e2.setSpaceId("2");
        e2.setName("Mohit");
        e2.setDepartment("Finance");
        e2.setSalary(80000);

        space.write(e1);
        space.write(e2);

        for (int i = 0; i < 5; i++) {
            Employee emp = new Employee();
            emp.setSpaceId(String.valueOf(i));
            emp.setName("EMP - " + String.valueOf(i));
            emp.setDepartment("Finance");
            emp.setSalary(80000);

            space.write(emp);

        }

        AsyncFuture<String> future = space.execute(new EmployeeTask());
        String result = future.get();
        System.out.println(result);

    }

    public static GigaSpace getGigaSpace(String url) {
        IJSpace space = new UrlSpaceConfigurer(url).space();

        GigaSpace gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();
        return gigaSpace;
    }
}
