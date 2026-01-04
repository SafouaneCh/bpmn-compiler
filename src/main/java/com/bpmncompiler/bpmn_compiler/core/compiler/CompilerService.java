package com.bpmncompiler.bpmn_compiler.core.compiler;

import com.bpmncompiler.bpmn_compiler.core.ast.BpmnProcess;
import com.bpmncompiler.bpmn_compiler.infra.parser.BpmnXmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class CompilerService {

    private final BpmnXmlParser parser;
    private final GeneratorFactory generatorFactory;

    // C'est le Pattern Template Method : L'algorithme global est fixé ici
    public String compile(InputStream source, String targetLanguage) {
        try {
            // 1. INPUT : Lire le XML -> AST
            BpmnProcess process = parser.parse(source);

            // 2. PROCESS : Validation simple (on vérifie juste qu'il y a des noeuds)
            if (process.getNodes().isEmpty()) {
                throw new IllegalStateException("Process is empty");
            }

            // 3. OUTPUT : Choisir le générateur et générer
            CodeGeneratorStrategy strategy = generatorFactory.getStrategy(targetLanguage);
            return strategy.generate(process);

        } catch (IOException e) {
            throw new RuntimeException("Compilation failed", e);
        }
    }
}
