package main.com.ambuj.examples;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

public class Person {
    private String spaceId;
    private String routingId;
    private String name;
    private Integer age;
    private String job;

    @SpaceId(autoGenerate = true)
    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @SpaceRouting
    public String getRoutingId() {
        return routingId;
    }

    public void setRoutingId(String routingId) {
        this.routingId = routingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Person{" +
                "spaceId='" + spaceId + '\'' +
                ", routingId='" + routingId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
    }
}
