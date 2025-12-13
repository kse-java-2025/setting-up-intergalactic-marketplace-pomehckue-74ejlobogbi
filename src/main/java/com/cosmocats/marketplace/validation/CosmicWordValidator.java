package com.cosmocats.marketplace.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private static final List<String> COSMIC_TERMS = Arrays.asList(
            "star", "galaxy", "comet", "cosmic", "space", "lunar", 
            "solar", "nebula", "orbit", "asteroid", "planet", "meteor",
            "satellite", "constellation", "supernova", "quasar", "pulsar",
            "black hole", "wormhole", "antimatter", "gravity", "celestial",
            "interstellar", "intergalactic", "milky way", "andromeda"
    );

    @Override
    public void initialize(CosmicWordCheck constraintAnnotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String lowerCaseValue = value.toLowerCase();
        return COSMIC_TERMS.stream().anyMatch(lowerCaseValue::contains);
    }
}
