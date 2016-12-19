package org.xxz.enums;

public enum EventType {
    
    USER_CREATED("USER_CREATED", "用户创建"),
    ;
    
    private String key;
    private String value;
    EventType(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() { return this.key; }
    public String getValue() { return this.value; }
}
