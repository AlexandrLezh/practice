package com.rimi.ecom.core.responces;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoreError {
    private String fieldTitle;
    private String message;
}
