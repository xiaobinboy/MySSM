package edu.gdpu.springmvc.servlet.view;

/**
 * @author mazebin
 * @date 2021年 04月17日 13:43:15
 */
public class MappingJsonValue {
    private Object value;

    public MappingJsonValue() {
    }

    public MappingJsonValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}