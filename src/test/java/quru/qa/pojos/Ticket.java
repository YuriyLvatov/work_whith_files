package quru.qa.pojos;

import java.util.List;

public class Ticket {

    private int ticketId;
    private int curatorId;
    private String curatorName;
    private List<Tasks> tasks;

    public int getTicketId() {
        return ticketId;
    }

    public int getCuratorId() {
        return curatorId;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

}
