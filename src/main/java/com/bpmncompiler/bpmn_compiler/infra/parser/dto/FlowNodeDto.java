package com.bpmncompiler.bpmn_compiler.infra.parser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

//this is our flow node dto
@Data
public abstract class FlowNodeDto {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String name;
}
