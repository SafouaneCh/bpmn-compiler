package com.bpmncompiler.bpmn_compiler.core.ast;

import com.bpmncompiler.bpmn_compiler.core.visitor.BpmnVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

//This is our task class it extends from BpmnNode
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Task extends BpmnNode {
    private String type;

    @Override
    public void accept(BpmnVisitor visitor) {
        visitor.visit(this);
    }
}
