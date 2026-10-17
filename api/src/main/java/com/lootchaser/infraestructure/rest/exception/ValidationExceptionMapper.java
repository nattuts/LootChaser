package com.lootchaser.infraestructure.rest.exception;

import com.lootchaser.infraestructure.rest.dto.ProblemDetails;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        });

        ProblemDetails problem = new ProblemDetails(
                "https://lootchaser.com/errors/validation-error",
                "Dados de entrada inválidos",
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Um ou mais campos não atendem aos requisitos de validação.",
                "/api/v1"
        );
        problem.invalidParams = errors;

        return Response.status(Response.Status.BAD_REQUEST)
                .header("Content-Type", "application/problem+json")
                .entity(problem)
                .build();
    }
}