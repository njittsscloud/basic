package com.tss.basic.site.response;

import com.tss.basic.site.exception.AbstractBusinessException;
import com.tss.basic.site.exception.ValidationInvalidParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
public class BasicResponseErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }

    @Autowired
    public BasicResponseErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = "${error.path:/error}")
    public ResponseEntity<ErrorDataResponse> error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable exception = this.errorAttributes.getError(requestAttributes);

        HttpStatus status;
        ErrorDataResponse response = new ErrorDataResponse();
        response.setSuccess(false);
        if (exception instanceof AbstractBusinessException) {
            AbstractBusinessException e = (AbstractBusinessException) exception;
            status = HttpStatus.OK;
            response.setErrorCode(e.getCode());
            response.setMsg((exception instanceof ValidationInvalidParamException) ? getErrorMessageFromData(e.getData(), e.getMessage()) : e.getMessage());
            response.setErrorData(e.getData());
        } else {
            status = this.getStatus(request);
            response.setErrorCode(ErrorDataResponse.UNKNOWN_CODE);
            response.setMsg(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        return new ResponseEntity<>(response, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String getErrorMessageFromData(Object data, String errorMessageDefault) {
        try {
            if (data != null && data instanceof Map && ((Map) data).size() > 0) {
                Object firstValue = ((Map) data).values().stream().findFirst().get();
                if (firstValue instanceof String) {
                    return (String) firstValue;
                }
            }
        } catch (Exception e) {
        }
        return errorMessageDefault;
    }
}
