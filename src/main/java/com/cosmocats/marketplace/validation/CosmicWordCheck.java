package com.cosmocats.marketplace.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface CosmicWordCheck {
    
    String message() default "Product name must contain at least one cosmic term (e.g., star, galaxy, comet, cosmic, space, lunar, solar, nebula, orbit, asteroid)";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
