package com.bpmncompiler.bpmn_compiler.infra.generator;

import com.bpmncompiler.bpmn_compiler.core.ast.BpmnNode;
import com.bpmncompiler.bpmn_compiler.core.ast.BpmnProcess;
import com.bpmncompiler.bpmn_compiler.core.compiler.CodeGeneratorStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JavaCodeGeneratorStrategy implements CodeGeneratorStrategy {

    @Override
    public boolean supports(String language) {
        return "JAVA".equalsIgnoreCase(language);
    }

    @Override
    public String generate(BpmnProcess process) {
        JavaBpmnVisitor visitor = new JavaBpmnVisitor();

        // 1. Indexer les noeuds par ID pour naviguer vite
        Map<String, BpmnNode> nodeMap = process.getNodes().stream()
                .collect(Collectors.toMap(BpmnNode::getId, node -> node));

        // 2. Trouver le point de départ
        BpmnNode currentNode = process.getStartEvent();

        // 3. Algorithme de parcours (Séquentiel simple : Tant qu'il y a un voisin, on
        // avance)
        while (currentNode != null) {
            // A. Visiter (Générer le code pour ce noeud)
            currentNode.accept(visitor);

            // B. Avancer vers le suivant
            String nextId = currentNode.getOutgoingFlowId();
            if (nextId != null && nodeMap.containsKey(nextId)) {
                currentNode = nodeMap.get(nextId);
            } else {
                currentNode = null; // Fin du chemin (ou lien cassé)
            }
        }

        return visitor.getCode();
    }
}
