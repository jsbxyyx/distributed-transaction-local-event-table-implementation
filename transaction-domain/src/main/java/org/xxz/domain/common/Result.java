package org.xxz.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements java.io.Serializable {
    
    private static final long serialVersionUID = 6044277953259380434L;
    
    private int c;
    private String m;
    private Object d;

}
