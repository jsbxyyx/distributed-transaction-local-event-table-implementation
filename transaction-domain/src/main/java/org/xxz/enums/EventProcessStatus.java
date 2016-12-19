package org.xxz.enums;

public enum EventProcessStatus {
    
    NEW("NEW", "新建"),
    PROCESSED("PROCESSED", "已处理"),
    ;
    
    private String key;
    private String value;
    EventProcessStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() { return this.key; }
    public String getValue() { return this.value; }

}
