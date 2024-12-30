package com.deity.location.common.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String type = "/errors/uncategorized";
    private String title;
    private String code;
    private String detail;
    private String instance = "/errors/uncategorized/location";

    public ErrorResponse(String title, int code) {
        this.title = title;
        this.code = String.valueOf(code);
        this.detail = title;
    }
}
