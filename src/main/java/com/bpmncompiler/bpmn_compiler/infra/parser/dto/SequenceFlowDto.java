package com.bpmncompiler.bpmn_compiler.infra.parser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

//this is our sequence flow dto
@Data
public class SequenceFlowDto {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String sourceRef;

    @JacksonXmlProperty(isAttribute = true)
    private String targetRef;
}
