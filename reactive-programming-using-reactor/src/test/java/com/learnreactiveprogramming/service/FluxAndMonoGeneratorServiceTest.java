package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        var namesFlux = fluxAndMonoGeneratorService.namesFLux();
        StepVerifier.create(namesFlux)
                //.expectNext("alex","ben","chloe")
                .expectNext("alex")
                .expectNextCount(2)
                .verifyComplete();
    }


    @Test
    void namesFLux_map() {
        int stringLength = 3;
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_map(stringLength);
        StepVerifier.create(namesFlux)
                //.expectNext("ALEX","BEN","CHLOE")
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFLux_immutability() {
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_immutability();
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFLux_flatmap() {
        int stringLength = 3;
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_flatmap(stringLength);
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }


    @Test
    void namesFLux_flatmap_async() {
        int stringLength = 3;
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_flatmap_async(stringLength);
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {    //preserves order but takes more type than flatmap with delay
        int stringLength = 4;
        var namesFLux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);
        StepVerifier.create(namesFLux)
                .expectNext("V", "I", "V", "E", "K", "Y", "A", "S", "H", "B")
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        int stringLength = 4;
        var value = fluxAndMonoGeneratorService.namesMono_flatMap(stringLength);
        StepVerifier.create(value)
                .expectNext(List.of("V", "I", "V", "E", "K"))
//                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatMapMany() {
        int stringlenght = 4;
        var value = fluxAndMonoGeneratorService.namesMono_flatMapMany(stringlenght);
        StepVerifier.create(value)
                .expectNext("V", "I", "V", "E", "K")
                .verifyComplete();
    }

    @Test
    void namesFLux_transform() {
        int stringLength = 3;
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_transform(stringLength);
        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void namesFLux_transform_1() {
        int stringLength = 6;
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_transform(stringLength);
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFLux_transform_switchifempty() {
        int stringLength = 6;
        var namesFlux = fluxAndMonoGeneratorService.namesFLux_transform_switchifempty(stringLength);
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concatwith_mono() {
        var concatFlux = fluxAndMonoGeneratorService.explore_concatwith_mono();
        StepVerifier.create(concatFlux)
                .expectNext("A", "B")
                .verifyComplete();
    }
}