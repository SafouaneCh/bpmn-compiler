package com.bpmncompiler.bpmn_compiler.core.ast;

import com.bpmncompiler.bpmn_compiler.core.visitor.BpmnVisitor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

//This is our abstract class for all nodes
@Data
@SuperBuilder
public abstract class BpmnNode {
    private String id;
    private String name;
    private String outgoingFlowId;

    public abstract void accept(BpmnVisitor visitor);
}
