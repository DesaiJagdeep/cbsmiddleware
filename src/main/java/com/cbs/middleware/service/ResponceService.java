package com.cbs.middleware.service;

import com.cbs.middleware.domain.Responce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ResponceService {

    @Autowired
    Responce responce;

    public Object success(int statusCode, String message, Object data) {
        responce.setStatusCode(statusCode);
        responce.setMessage(message);
        responce.setStatus("success");
        responce.setData(data);
        return responce;
    }

    public Object failure(int statusCode, String message) {
        responce.setStatusCode(statusCode);
        responce.setMessage(message);
        responce.setStatus("failure");

        return responce;
    }
}
