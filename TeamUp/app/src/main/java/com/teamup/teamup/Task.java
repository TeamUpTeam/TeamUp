package com.teamup.teamup;

/**
 * Created by Robbie on 2/22/2016.
 */
public class Task {
    public String taskName;
    public String taskDescription;
    public User claimedBy;
    public String deadline;
    public boolean approved;
    public Task(String taskName, String taskDescription, User claimedBy, String deadline, boolean approved)
    {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.claimedBy = claimedBy;
        this.deadline = deadline;
        this.approved = approved;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Task: " + taskName);
        sb.append("\tDescription: " + taskDescription);
        sb.append("\tClaimed by: " + claimedBy);
        sb.append("\tDeadline: " + deadline);
        sb.append("\tApproved: " + approved);
        return sb.toString();
    }
}
