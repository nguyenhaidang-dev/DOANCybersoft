package com.nhom91.drugstore.exception;

import com.nhom91.drugstore.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CentralExceptions {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        BaseResponse response = new BaseResponse();
        response.setCode(400);
        response.setMessage(ex.getMessage());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        BaseResponse response = new BaseResponse();
        response.setCode(401);
        response.setMessage(ex.getMessage());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        BaseResponse response = new BaseResponse();
        response.setCode(404);
        response.setMessage(ex.getMessage());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        BaseResponse response = new BaseResponse();
        response.setCode(400);
        String message = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        response.setMessage(message);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<?> handleAccountLockedExceptions(AccountLockedException e){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(403);
        baseResponse.setMessage(e.getMessage());
        Map<String, Object> data = new HashMap<>();
        data.put("lockType", e.getLockType());
        if("TEMPORARY".equals(e.getLockType())){
            data.put("remainingSeconds", e.getRemainingSeconds());
            data.put("remainingMinutes", e.getRemainingSeconds() / 60);
        }
        baseResponse.setData(data);
        return ResponseEntity.status(403).body(baseResponse);
    }
}

