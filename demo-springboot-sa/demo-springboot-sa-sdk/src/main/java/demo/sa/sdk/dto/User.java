package demo.sa.sdk.dto;

import java.util.List;

public class User {

    private long uid;

    private String name;

    private List<Insterest> insterests;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Insterest> getInsterests() {
        return insterests;
    }

    public void setInsterests(List<Insterest> insterests) {
        this.insterests = insterests;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", insterests=" + insterests +
                '}';
    }
}
