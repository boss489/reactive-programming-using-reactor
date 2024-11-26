package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

	void testnamesFlux_map(){
		StepVerifier.create(fluxAndMonoGeneratorService.nameFlux_map(3))
			.expectNext("A", "B", "C")
			.verifyComplete();

	}

	@Test
	void testnamesFlux_immutable(){
		StepVerifier.create(fluxAndMonoGeneratorService.nameFlux_map(4))
			.expectNext("A", "B", "C")
			.verifyComplete();

	}

	@Test
	void testFlux_flatMap(){
		StepVerifier.create(fluxAndMonoGeneratorService.nameFlux_flatMap(3))
			.expectNextCount(9)
			.verifyComplete();
	}

	@Test
	void testFlux_concatMap(){
		StepVerifier.create(fluxAndMonoGeneratorService.nameFlux_concatMap(3))
			.expectNextCount(9)
			.verifyComplete();
	}

	@Test
	void testGenerateFlux() {

		Flux<String> stringFlux = fluxAndMonoGeneratorService.generateFlux();
		StepVerifier.create(stringFlux)
			.expectNext("A")
			.expectNext("B")
			.verifyComplete();
	}

	@Test
	void testGetMono() {
		Mono<String> stringMono = fluxAndMonoGeneratorService.getMono();
		StepVerifier.create(stringMono)
			.expectNext("A")
			.verifyComplete();
	}

	@Test
	void namesMono_flatMap() {
		int StringLengh = 3;
		Mono<List<String>> listMono = fluxAndMonoGeneratorService.namesMono_flatMap(3);

		StepVerifier.create(listMono)
			.expectNext(List.of("A", "L", "E", "X"))
			.verifyComplete();
	}

	@Test
	void nameFlux_transform() {

		int StringLengh = 1;
		Flux<String> stringFlux = fluxAndMonoGeneratorService.nameFlux_transform(StringLengh);

		StepVerifier.create(stringFlux)
			.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E", "B", "E", "N")
			.verifyComplete();
	}

	@Test
	void nameFlux_transform_switchIfEmpty() {
		int StringLengh = 10;
		Flux<String> stringFlux = fluxAndMonoGeneratorService.nameFlux_transform_switchIfEmpty(StringLengh);

		StepVerifier.create(stringFlux)
			.expectNext("D", "E", "F", "A", "U", "L", "T")
			.verifyComplete();
	}
}