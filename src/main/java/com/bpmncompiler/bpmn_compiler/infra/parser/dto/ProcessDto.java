package com.bpmncompiler.bpmn_compiler.infra.parser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import java.util.List;

//this is our process dto c'est la classe qui va contenir les processus
@Data
public class ProcessDto {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "startEvent")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<StartEventDto> startEvents;

    @JacksonXmlProperty(localName = "task")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TaskDto> tasks;

    @JacksonXmlProperty(localName = "endEvent")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EndEventDto> endEvents;

    @JacksonXmlProperty(localName = "sequenceFlow")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SequenceFlowDto> sequenceFlows;
}
