package com.bpmncompiler.bpmn_compiler.infra.parser;

import com.bpmncompiler.bpmn_compiler.core.ast.*;
import com.bpmncompiler.bpmn_compiler.infra.parser.dto.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//this is our bpmn xml parser
@Service
public class BpmnXmlParser {

    private final XmlMapper xmlMapper;

    public BpmnXmlParser() {
        this.xmlMapper = new XmlMapper();
        // Ignore tags we don't map (like bpmndi:BPMNDiagram) to avoid errors
        this.xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // stream (fichier xml) -> dto (vrac)
    public BpmnProcess parse(InputStream inputStream) throws IOException {
        // 1. Désérialisation Technique (Jackson)
        DefinitionsDto definitions = xmlMapper.readValue(inputStream, DefinitionsDto.class);

        // 2. Conversion Logique (DTO -> Domaine)
        return mapToDomain(definitions.getProcess());
    }

    private BpmnProcess mapToDomain(ProcessDto processDto) {
        if (processDto == null) {
            throw new IllegalArgumentException("No process found in definitions");
        }

        List<BpmnNode> nodes = new ArrayList<>();
        Map<String, BpmnNode> nodeMap = new HashMap<>();

        // --- Step A: Instantiate Nodes ---

        // Map Start Events
        if (processDto.getStartEvents() != null) {
            for (StartEventDto dto : processDto.getStartEvents()) {
                StartEvent node = StartEvent.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build();
                nodes.add(node);
                nodeMap.put(node.getId(), node);
            }
        }

        // Map Tasks
        if (processDto.getTasks() != null) {
            for (TaskDto dto : processDto.getTasks()) {
                Task node = Task.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .type("serviceTask") // Default fixed type for this simple project
                        .build();
                nodes.add(node);
                nodeMap.put(node.getId(), node);
            }
        }

        // Map End Events
        if (processDto.getEndEvents() != null) {
            for (EndEventDto dto : processDto.getEndEvents()) {
                EndEvent node = EndEvent.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build();
                nodes.add(node);
                nodeMap.put(node.getId(), node);
            }
        }

        // --- Step B: Link Nodes (Graph Reconstruction) ---

        if (processDto.getSequenceFlows() != null) {
            System.out.println("Found " + processDto.getSequenceFlows().size() + " sequence flows.");
            for (SequenceFlowDto flow : processDto.getSequenceFlows()) {
                System.out.println("Processing flow: " + flow.getId() + " from " + flow.getSourceRef() + " to "
                        + flow.getTargetRef());
                BpmnNode sourceNode = nodeMap.get(flow.getSourceRef());

                if (sourceNode != null) {
                    System.out.println("Link established: " + sourceNode.getName() + " -> " + flow.getTargetRef());
                    // We link the source node to its target via ID
                    sourceNode.setOutgoingFlowId(flow.getTargetRef());
                } else {
                    System.out.println("WARNING: Source node not found for ref: " + flow.getSourceRef());
                }
            }
        } else {
            System.out.println("No sequence flows found in DTO.");
        }

        return BpmnProcess.builder()
                .id(processDto.getId())
                .name(processDto.getName())
                .nodes(nodes)
                .build();
    }
}
