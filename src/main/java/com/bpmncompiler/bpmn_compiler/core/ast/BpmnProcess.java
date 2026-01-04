package com.bpmncompiler.bpmn_compiler.core.ast;

import lombok.Builder;
import lombok.Data;
import java.util.List;

//This our process class it contains all the nodes it knows only the start event
@Data
@Builder
public class BpmnProcess {
    private String id;
    private String name;
    private List<BpmnNode> nodes;

    // this method return the start event
    public StartEvent getStartEvent() {
        return nodes.stream()
                .filter(node -> node instanceof StartEvent)
                .map(node -> (StartEvent) node)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No StartEvent found in process"));
    }
}
