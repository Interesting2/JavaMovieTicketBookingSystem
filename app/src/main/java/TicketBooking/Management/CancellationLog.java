package TicketBooking.Management;

public class CancellationLog {
    private String time;
    private String userName;
    private String reason;

    public CancellationLog(String time, String userName, String reason) {
        this.time = time;
        this.userName = userName;
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public String getUserName() {
        return userName;
    }

    public String getReason() {
        return reason;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "CancellationLog{" +
                "time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
