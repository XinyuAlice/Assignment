/*CSCI2110-Assignment4-Process
the program is to create object of process
<Xinyu,Liu><B00783546><11.21>
*/
public class Process {
    //attributes
    private int id;
    private int timeReqd;
    private int priority;
    private int timeArrival;
    //constructor
    public Process(int id, int timeReqd, int priority, int timeArrival) {
        this.id = id;
        this.timeReqd = timeReqd;
        this.priority = priority;
        this.timeArrival = timeArrival;
    }
    //get,	set	and	other	methods	as	required
    public int getId() {
        return id;
    }

    public int getTimeReqd() {
        return timeReqd;
    }

    public int getPriority() {
        return priority;
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeReqd(int timeReqd) {
        this.timeReqd = timeReqd;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTimeArrival(int timeArrival) {
        this.timeArrival = timeArrival;
    }
    //compareTo method
    public int compareTo(Process p) {
        int result;
        if (this.getPriority() > p.getPriority()) {
            result = 1;
        }
        else if (this.getPriority() < p.getPriority()) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }
    //toString method
    public String toString(){
        return "("+this.getId()+","+this.getTimeReqd()+","+this.getPriority()+")";
    }


}
