package com.datn.drugstore.utils;

import com.datn.drugstore.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static ResponseEntity<BaseResponse> success(Object data) {
        BaseResponse res = new BaseResponse();
        res.setCode(200);
        res.setMessage("Success");
        res.setData(data);
        return ResponseEntity.ok(res);
    }

    public static ResponseEntity<BaseResponse> success(Object data, String message) {
        BaseResponse res = new BaseResponse();
        res.setCode(200);
        res.setMessage(message);
        res.setData(data);
        return ResponseEntity.ok(res);
    }

    public static ResponseEntity<BaseResponse> successMessage(String message) {
        BaseResponse res = new BaseResponse();
        res.setCode(200);
        res.setMessage(message);
        res.setData(null);
        return ResponseEntity.ok(res);
    }

    public static ResponseEntity<BaseResponse> created(Object data) {
        BaseResponse res = new BaseResponse();
        res.setCode(201);
        res.setMessage("Created successfully");
        res.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    public static ResponseEntity<BaseResponse> error(int code, String message, HttpStatus status) {
        BaseResponse res = new BaseResponse();
        res.setCode(code);
        res.setMessage(message);
        res.setData(null);
        return ResponseEntity.status(status).body(res);
    }

    public static ResponseEntity<BaseResponse> badRequest(String message) {
        return error(400, message, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<BaseResponse> unauthorized(String message) {
        return error(401, message, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<BaseResponse> forbidden(String message) {
        return error(403, message, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<BaseResponse> notFound(String message) {
        return error(404, message, HttpStatus.NOT_FOUND);
    }
}
