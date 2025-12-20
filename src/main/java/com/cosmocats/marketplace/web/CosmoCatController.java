package com.cosmocats.marketplace.web;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.featuretoggle.FeatureToggles;
import com.cosmocats.marketplace.featuretoggle.annotation.FeatureToggle;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/products")
public class CosmoCatController {

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<String>> getCosmoCats() {
        return ResponseEntity.ok(List.of(
                "Nebula Whiskerborn",
                "Captain Cometpaw",
                "Starlight Purrion",
                "Lunar Whiskerflare"
        ));
    }
}
