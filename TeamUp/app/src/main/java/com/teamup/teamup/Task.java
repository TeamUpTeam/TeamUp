package com.teamup.teamup;

/**
 * Created by Robbie on 2/22/2016.
 */
public class Task {
    public String taskName;
    public int taskID;
    public String taskDescription;
    public User postedBy;
    public User claimedBy;
    public String deadline;
    public boolean complete;
    public boolean approved;

    public Task(String taskName, int taskID, String taskDescription, User postedBy, User claimedBy, String deadline, boolean complete, boolean approved)
    {
        this.taskName = taskName;
        this.taskID = taskID;
        this.taskDescription = taskDescription;
        this.claimedBy = claimedBy;
        this.deadline = deadline;
        this.complete = complete;
        this.approved = approved;
    }

    /*
     *  Returns the name of the project as a string
     */
    public String getTaskName (Task task)
    {
        return task.taskName;
    }

    /*
     *  Returns the ProjectID as an integer
     */
    public int getTaskID (Task task)
    {
        return task.taskID;
    }

    /*
     *  Returns the description of the project as a string
     */
    public String getTaskDescription (Task task)
    {
        return task.taskDescription;
    }

    /*
     *  Returns the User that posted the task
     */
    public User getPostedBy (Task task)
    {
        return task.postedBy;
    }

    /*
     *  Returns the User that claimed the task
     */
    public User getClaimedBy (Task task)
    {
        return task.claimedBy;
    }

    /*
     *  Returns the deadline of the project as a string
     */
    public String getTaskDeadline (Task task)
    {
        return task.deadline;
    }

    /*
     *  Returns the boolean value for if the task has been completed
     */
    public boolean isTaskComplete (Task task)
    {
        return task.complete;
    }

    /*
     *  Returns the boolean value for if the task has been approved
     */
    public boolean isTaskApproved (Task task)
    {
        return task.approved;
    }

    /*
     *  Returns true if the task description was changed successfully, and false if something went wrong
     */
    public boolean setTaskDescription (String newDescription)
    {
        //httpclient request here
        if(true) {
            this.taskDescription = newDescription;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the User claimed the task, false if something went wrong
     */
    public boolean setClaimedBy (User claimer)
    {
        //httpclient request here
        if(true) {
            this.claimedBy = claimer;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the deadline was changed successfully, and false if something went wrong
     */
    public boolean setDeadline (String newDeadline)
    {
        //httpclient request here
        if(true) {
            this.deadline= newDeadline;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the task was set to incomplete, and false if something went wrong
     */
    public boolean setCompleteF ()
    {
        //httpclient request here
        if(true) {
            this.complete = false;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the task was set to complete, and false if something went wrong
     */
    public boolean setCompleteT ()
    {
        //httpclient request here
        if(true) {
            this.complete = true;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the task was set to not approved, and false if something went wrong
     */
    public boolean setApprovedF ()
    {
        //httpclient request here
        if(true) {
            this.approved = false;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the task was set to approved, and false if something went wrong
     */
    public boolean setApprovedT ()
    {
        //httpclient request here
        if(true) {
            this.approved = true;
            return true;
        }

        return false;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Task: " + taskName);
        sb.append("\tTask ID: " + taskID);
        sb.append("\tDescription: " + taskDescription);
        sb.append("\tClaimed by: " + claimedBy);
        sb.append("\tDeadline: " + deadline);
        sb.append("\tApproved: " + approved);
        return sb.toString();
    }
}
