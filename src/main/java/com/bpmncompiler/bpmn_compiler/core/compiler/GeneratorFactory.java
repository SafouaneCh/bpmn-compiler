package com.bpmncompiler.bpmn_compiler.core.compiler;

import org.springframework.stereotype.Component;
import java.util.List;

//this is our generator factory
@Component
public class GeneratorFactory {

    // Spring injecte automagiquement TOUTES les classes qui implémentent
    // CodeGeneratorStrategy
    private final List<CodeGeneratorStrategy> strategies;

    public GeneratorFactory(List<CodeGeneratorStrategy> strategies) {
        this.strategies = strategies;
    }

    public CodeGeneratorStrategy getStrategy(String language) {
        return strategies.stream()
                .filter(s -> s.supports(language))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No generator found for language: " + language));
    }
}
