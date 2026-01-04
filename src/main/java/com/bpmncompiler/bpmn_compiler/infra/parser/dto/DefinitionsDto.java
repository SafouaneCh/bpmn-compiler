package com.bpmncompiler.bpmn_compiler.infra.parser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//this is our definitions dto
@Data
@JacksonXmlRootElement(localName = "definitions")
public class DefinitionsDto {

    @JacksonXmlProperty(localName = "process")
    private ProcessDto process;
}
