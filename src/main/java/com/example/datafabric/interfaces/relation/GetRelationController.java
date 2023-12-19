package com.example.datafabric.interfaces.relation;

import com.example.datafabric.application.relation.port.GetRelationResultUseCase;
import com.example.datafabric.domain.RelationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/datafabric/relation")
@RestController
@RequiredArgsConstructor
public class GetRelationController {

    private final GetRelationResultUseCase getRelationResultUseCase;

    @GetMapping("/byDataObject")
    public RelationResult getByDataObject(
        @RequestParam(value = "dataObjectId", required = true) String dataObjectId,
        @RequestParam(value = "nodeMax", required = false) Integer nodeMax) {
        return getRelationResultUseCase.getByDataObject(dataObjectId, nodeMax);
    }
}
