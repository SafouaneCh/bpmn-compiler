package com.bpmncompiler.bpmn_compiler.core.visitor;

import com.bpmncompiler.bpmn_compiler.core.ast.EndEvent;
import com.bpmncompiler.bpmn_compiler.core.ast.StartEvent;
import com.bpmncompiler.bpmn_compiler.core.ast.Task;

//This is our visitor interface it visits all nodes
public interface BpmnVisitor {
    void visit(StartEvent startEvent);

    void visit(Task task);

    void visit(EndEvent endEvent);
}
