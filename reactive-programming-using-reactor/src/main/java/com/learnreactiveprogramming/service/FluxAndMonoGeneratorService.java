package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	public Flux<String> generateFlux() {
		return Flux.fromIterable(List.of("alex", "chloe", "ben")).log();
	}
	public Mono<String> getMono() {
		return Mono.just("A");
	}
	public Flux<String> nameFlux_map(int length) {
		return Flux.fromIterable(List.of("alex", "chloe", "ben"))
				.map(String::toUpperCase)
				.filter(s -> s.length() > length)
				.log();
	}

	public Flux<String> nameFlux_flatMap(int stringLength) {
		return Flux.fromIterable(List.of("alex", "chloe", "ben"))
			.map(String::toUpperCase)
			.filter(s -> s.length() > stringLength)
			.flatMap(this::splitStringWithDelay)
			.log();
	}

	public Flux<String> nameFlux_transform(int stringLength) {
		Function<Flux<String>, Flux<String>> filterMap  = name -> name
			.map(String::toUpperCase)
			.filter(s -> s.length() > stringLength)
			.flatMap(this::splitString);

		return Flux.fromIterable(List.of("alex", "chloe", "ben"))
			.transform(filterMap)
			.log();
	}

	public Flux<String> nameFlux_transform_switchIfEmpty(int stringLength) {
		Function<Flux<String>, Flux<String>> filterMap  = name -> name
			.map(String::toUpperCase)
			.filter(s -> s.length() > stringLength)
			.flatMap(this::splitString);

		var defaultFlux = Flux.just("default")
			.map(String::toUpperCase)
			.flatMap(this::splitString);

		return Flux.fromIterable(List.of("alex", "chloe", "ben"))
			.transform(filterMap)
			.switchIfEmpty(defaultFlux)
			.log();
	}

	public Flux<String> nameFlux_concatMap(int stringLength) {
		return Flux.fromIterable(List.of("alex", "chloe", "ben"))
			.map(String::toUpperCase)
			.filter(s -> s.length() > stringLength)
			.concatMap(this::splitStringWithDelay)
			.log();
	}

	public Flux<String> nameFlux_flatMap_async(int stringLength) {
		return Flux.fromIterable(List.of("alex", "chloe", "ben"))
			.map(String::toUpperCase)
			.filter(s -> s.length() > stringLength)
			.flatMap(this::splitStringWithDelay)
			.log();
	}

	public Flux<String> splitString(String name) {
		var charArray = name.split("");
		return Flux.fromArray(charArray);
	}

	public Flux<String> splitStringWithDelay(String name) {
		var charArray = name.split("");
		int i = 1000;
		return Flux.fromArray(charArray)
			.delayElements(Duration.ofMillis(i));
	}

	public Mono<String> namesMono_mapfilter(int StringLength){
		return Mono.just("alex")
			.map(String::toUpperCase)
			.filter(s -> s.length() > StringLength)
			.log();
	}

	public Mono<List<String>> namesMono_flatMap(int StringLength){
		return Mono.just("alex")
			.map(String::toUpperCase)
			.filter(s -> s.length() > StringLength)
			.flatMap(this::splitStringMono);
	}

	public Flux<String> namesMono_flatMapMany(int StringLength){
		return Mono.just("alex")
			.map(String::toUpperCase)
			.filter(s -> s.length() > StringLength)
			.flatMapMany(this::splitString);
	}



	private Mono<List<String>> splitStringMono(String s) {
		var charArray = s.split("");
		List<String> charArray1 = List.of(charArray);
		return Mono.just(charArray1);

	}

	public static void main(String[] args) {
		FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
		// fluxAndMonoGeneratorService.generateFlux()
		// 		.subscribe(System.out::println);
		//
		// fluxAndMonoGeneratorService.getMono()
		// 		.subscribe(System.out::println);
		//
		// fluxAndMonoGeneratorService.nameFlux_flatMap(4)
		// 		.subscribe(System.out::println);

		fluxAndMonoGeneratorService.nameFlux_flatMap(4)
			.subscribe(System.out::println);

		fluxAndMonoGeneratorService.nameFlux_concatMap(4)
			.subscribe(System.out::println);

	}
}
