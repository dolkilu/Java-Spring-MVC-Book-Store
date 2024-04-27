package hkmu.comps380f.s1326557_project.model;

public class RoleOption {
    private String key;
    private String value;

    public RoleOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RoleOption() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
