package main.com.ambuj.examples;

import com.gigaspaces.async.AsyncResult;
import com.gigaspaces.executor.DistributedSpaceTask;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;
import net.jini.core.transaction.Transaction;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoAware;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.executor.TaskGigaSpace;

import java.util.List;

public class EmployeeTask implements DistributedTask<Integer, String>, ClusterInfoAware {

    @TaskGigaSpace
    private transient GigaSpace gigaSpace;

    private ClusterInfo clusterInfo;

    @Override
    public String reduce(List<AsyncResult<Integer>> results) throws Exception {
        Integer totalExpense = 0;
        for (AsyncResult<Integer> result : results) {
            if (result.getException() != null) {
                throw result.getException();
            }
            totalExpense += result.getResult();
        }
        System.out.println("Total company expense on updating all salaries is " + totalExpense);
        return "Total company expense on updating all salaries is " + totalExpense;
    }


    @Override
    public Integer execute() throws Exception {
        Integer totalSalaryInPartition = 0;
        Integer newSalary = 100000;
        SQLQuery<Employee> allEmployeesTemplate = new SQLQuery<>(Employee.class, "");
        Employee[] arr = gigaSpace.readMultiple(allEmployeesTemplate);
        for (Employee emp : arr) {
            emp.setSalary(newSalary);
            totalSalaryInPartition += newSalary;
        }

        gigaSpace.writeMultiple(arr);
        return totalSalaryInPartition;
    }


    @Override
    public void setClusterInfo(ClusterInfo clusterInfo) {
        this.clusterInfo = clusterInfo;
    }
}
