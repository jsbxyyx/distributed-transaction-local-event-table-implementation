package org.xxz.enums;

public enum EventPublishStatus {
    
    NEW("NEW", "新建"),
    PUBLISHED("PUBLISHED", "已发布"),
    ;
    
    private String key;
    private String value;
    EventPublishStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() { return this.key; }
    public String getValue() { return this.value; }

}
