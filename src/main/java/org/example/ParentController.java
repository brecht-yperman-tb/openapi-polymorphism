package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParentController {
	private final List<ParentProvider<?>> parentProviders;

	public ParentController(List<ParentProvider<?>> parentProviders) {
		this.parentProviders = parentProviders;
	}

	@GetMapping("/")
	public List<Parent> getObjects() {
		return parentProviders.stream()
				.map(ParentProvider::provide)
				.collect(Collectors.toList());
	}

}