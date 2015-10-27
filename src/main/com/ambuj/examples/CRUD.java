package main.com.ambuj.examples;

import com.gigaspaces.client.ReadModifiers;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

public class CRUD {

    private GigaSpace space;

    public void writeSome() {

        //writing a single object
        Person p1 = new Person();
        p1.setName("Ambuj");
        p1.setAge(27);
        p1.setJob("TA");
        p1.setRoutingId(p1.getName());

        space.write(p1);

        //writing multiple objects
        Employee e1 = new Employee();
        e1.setName("Anuj");
        e1.setDepartment("Accounting");
        e1.setSalary(40000);

        Employee e2 = new Employee();
        e2.setName("Mohit");
        e2.setDepartment("Finance");
        e2.setSalary(80000);

        Employee[] arr = {e1, e2};
        space.writeMultiple(arr);
    }

    public void readSome() {
        SQLQuery<Employee> template = new SQLQuery<>(Employee.class, "name = ?");
        template.setParameter(1, "Anuj");

        //Read single object
        Employee emp = space.read(template);

        //Read multiple object
        Employee[] empArr = space.readMultiple(template, Integer.MAX_VALUE);

        //Read in exclusive lock mode
        Employee empExclusive = space.read(template, 5000, ReadModifiers.EXCLUSIVE_READ_LOCK);

        //Read by Id
        Employee empById = space.readById(Employee.class, "1");
    }

    public void updateSome() {
        SQLQuery<Employee> template = new SQLQuery<>(Employee.class, "name = ?");
        template.setParameter(1, "Anuj");

        Employee emp = space.read(template, 5000, ReadModifiers.EXCLUSIVE_READ_LOCK);

        emp.setSalary(80000);
        space.write(emp);
    }

    public void deleteSome() {
        SQLQuery<Employee> template = new SQLQuery<>(Employee.class, "name = ?");
        template.setParameter(1, "Anuj");

        Employee emp = space.take(template);

        space.clear(new Person());
    }

    public GigaSpace getSpace() {
        return space;
    }

    public void setSpace(GigaSpace space) {
        this.space = space;
    }
}
