package demo.sa.sdk.dto;

public class Insterest {

    private String name;

    private int code;

    public Insterest() {
    }

    public Insterest(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Insterest{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
