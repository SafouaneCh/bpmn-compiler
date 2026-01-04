package com.bpmncompiler.bpmn_compiler.core.compiler;

import com.bpmncompiler.bpmn_compiler.core.ast.BpmnProcess;

public interface CodeGeneratorStrategy {
    // Permet de savoir si cette stratégie sait gérer le langage demandé
    boolean supports(String language);

    // Lance la génération du code
    String generate(BpmnProcess process);
}
