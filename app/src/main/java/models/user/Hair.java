package models.user;

import java.io.Serializable;

public class Hair implements Serializable {
    private String color;
    private String type;

    public Hair(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Hair{" +
                "color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
