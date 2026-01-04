package com.bpmncompiler.bpmn_compiler.infra.generator;

import com.bpmncompiler.bpmn_compiler.core.ast.EndEvent;
import com.bpmncompiler.bpmn_compiler.core.ast.StartEvent;
import com.bpmncompiler.bpmn_compiler.core.ast.Task;
import com.bpmncompiler.bpmn_compiler.core.visitor.BpmnVisitor;

public class JavaBpmnVisitor implements BpmnVisitor {

    // mon code sera stocké ici
    private final StringBuilder builder = new StringBuilder();

    public String getCode() {
        return builder.toString();
    }

    @Override
    public void visit(StartEvent startEvent) {
        builder.append("public class GeneratedProcess {\n");
        builder.append("    public static void main(String[] args) {\n");
        builder.append("        System.out.println(\"Process Started: ").append(startEvent.getName()).append("\");\n");
    }

    @Override
    public void visit(Task task) {
        builder.append("        // Executing task ").append(task.getId()).append("\n");
        builder.append("        System.out.println(\"Executing Task: ").append(task.getName()).append("\");\n");
    }

    @Override
    public void visit(EndEvent endEvent) {
        builder.append("        System.out.println(\"Process Ended: ").append(endEvent.getName()).append("\");\n");
        builder.append("    }\n");
        builder.append("}\n");
    }
}
