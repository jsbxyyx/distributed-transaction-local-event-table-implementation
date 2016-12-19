package org.xxz.domain.event;

import java.util.Date;

import lombok.Data;

@Data
public class EventProcess implements java.io.Serializable {

    private static final long serialVersionUID = -473642727217022453L;

    private String id;
    /** org.xxz.enums.EventProcessStatus */
    private String status;
    private String payload;
    private Date createTime;
    /** org.xxz.enums.EventType */
    private String eventType;
    
}
