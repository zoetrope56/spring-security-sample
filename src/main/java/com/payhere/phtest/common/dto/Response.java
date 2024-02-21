package com.payhere.phtest.common.dto;

import com.payhere.phtest.common.enumulation.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private int code;
    private String message;
    private T data;

    public Response(String message) {
        this.message = message;
    }

    public static <T> ResponseEntity<Response<T>> success(ResponseCode code) {
        return ResponseEntity
                .status(code.getCode())
                .body(new Response<>(code.getMessage()));
    }

    public static <T> ResponseEntity<Response<T>> success(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getCode())
                .body(new Response<>(code.getCode(), code.getMessage(), data));
    }

    public static <T> ResponseEntity<Response<T>> error(ResponseCode code) {
        return ResponseEntity
                .status(code.getCode())
                .body(new Response<>(code.getMessage()));
    }

    public static <T> ResponseEntity<Response<T>> error(ResponseCode code, String message) {
        return ResponseEntity
                .status(code.getCode())
                .body(new Response<>(message));
    }
}

