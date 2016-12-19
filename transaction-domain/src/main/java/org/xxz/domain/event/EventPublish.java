package org.xxz.domain.event;

import java.util.Date;

import lombok.Data;

@Data
public class EventPublish implements java.io.Serializable {
    
    private static final long serialVersionUID = 2875490452115335136L;
    
    private String id;
    /** org.xxz.enums.EventPublishStatus */
    private String status;
    private String payload;
    private Date createTime;
    /** org.xxz.enums.EventType */
    private String eventType;
    
}

