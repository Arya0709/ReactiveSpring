package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFLux() { //publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .log(); //db or remote service call
    }

    public Mono<String> nameMono() {
        return Mono.just("alex").log();
    }

    public Flux<String> namesFLux_map(int stringLength) { //publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s)
                .log(); //db or remote service call
    }

    public Flux<String> namesFLux_immutability() { //publisher
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;

    }

    public Flux<String> namesFLux_flatmap(int stringLength) { //publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s))
                .log(); //db or remote service call
    }

    public Flux<String> namesFLux_flatmap_async(int stringLength) { //publisher
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString_withDelay(s))
                .log(); //db or remote service call
    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        return Flux.fromIterable(List.of("vivek", "arya", "yashB"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(s -> splitString_withDelay(s))
                .log();
    }

    public Flux<String> namesFLux_transform(int stringLength) { //publisher
        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filtermap)
                .flatMap(s -> splitString(s))
                .defaultIfEmpty("default")
                .log(); //db or remote service call
    }

    public Flux<String> namesFLux_transform_switchifempty(int stringLength) { //publisher
        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s));
        var defaultflux = Flux.just("default")
                .transform(filtermap);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filtermap)
                .switchIfEmpty(defaultflux)
                .log(); //db or remote service call
    }


    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_withDelay(String name) {
        var charArray = name.split("");
        var delay = 10000;
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public Mono<List<String>> namesMono_flatMap(int stringLength) {
        return Mono.just("vivek")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono).log();
    }

    public Flux<String> explore_concat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> explore_concatwith_mono() {
        var aMono = Mono.just("A");
        var bMono = Flux.just("B");
        return aMono.concatWith(bMono).log();
    }

    public Flux<String> explore_concatwith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> namesMono_flatMapMany(int stringLength) {
        return Mono.just("vivek")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString).log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }


    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFLux().subscribe(name -> {
            System.out.println("Name is: " + name);
        });
        fluxAndMonoGeneratorService.nameMono().subscribe(name -> {
            System.out.println("Mono name is: " + name);
        });

        fluxAndMonoGeneratorService.namesFLux_flatmap_async(3).subscribe(name ->
                System.out.println(name));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

