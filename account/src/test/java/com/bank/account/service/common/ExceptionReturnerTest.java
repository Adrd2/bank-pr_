package com.bank.account.service.common;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionReturnerTest {

    @Test
    void testGetEntityNotFoundException() {
        String message = "Entity not found";
        EntityNotFoundException exception = new ExceptionReturner().getEntityNotFoundException(message);

        assertAll(
                () -> {
                    assertNotNull(exception);
                    assertEquals(message, exception.getMessage());
                }
        );
    }
}