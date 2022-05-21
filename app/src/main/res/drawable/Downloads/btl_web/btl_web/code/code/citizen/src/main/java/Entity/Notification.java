package Entity;

import Model.ModelCadres;

public class Notification {
    private int ordinal;
    private String src;
    private String dest;
    private String time;
    private String area;
    private boolean status;

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Notification(int ordinal, String src, String dest, String time, String area, boolean status) {
        this.ordinal = ordinal;
        this.src = src;
        this.dest = dest;
        this.time = time;
        this.area = area;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "ordinal=" + ordinal +
                ", src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
