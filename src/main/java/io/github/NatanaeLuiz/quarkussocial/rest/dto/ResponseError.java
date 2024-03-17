package io.github.NatanaeLuiz.quarkussocial.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private String message;
    private Collection<FieldError> erros;

    public ResponseError(String message, List<FieldError> erros) {
        this.message = message;
        this.erros = erros;
    }

    public static <T> ResponseError createFromValidation(
            Set<ConstraintViolation<T>> violations){

        List<FieldError> erros = violations
                .stream()
                .map( cv -> new FieldError( cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String message = "Validation Error";

        var responseError = new ResponseError(message, erros);

        return responseError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FieldError> getErros() {
        return erros;
    }

    public void setErros(Collection<FieldError> erros) {
        this.erros = erros;
    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(this).build();
    }
}
