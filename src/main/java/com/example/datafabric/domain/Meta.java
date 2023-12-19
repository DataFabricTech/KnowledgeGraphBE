package com.example.datafabric.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Meta {


    private LocalDateTime learningDate;
    private LocalDateTime applyDate;
    private String method;
    private List<String> algorithm;

    public static Meta create(LocalDateTime learningDate, LocalDateTime applyDate, String method,
        List<String> algorithm) {
        return new Meta(learningDate, applyDate, method, algorithm);
    }

    public static Meta create(String learningDate, String applyDate, String method, List<String> algorithm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        return new Meta(LocalDateTime.parse(learningDate.trim(), formatter),
            LocalDateTime.parse(applyDate.trim(), formatter), method, algorithm);
    }

    public static Meta translateFromString(String meta) {
        if (meta == null) {
            //TODO: EXCEPTION
        }
        List<String> metaString = List.of(meta.split(",")).stream().map(s -> s.replaceAll("\"", ""))
            .collect(Collectors.toList());
        List<String> algorithm = metaString.subList(3, metaString.size()).stream()
            .map(item -> item.replaceAll("[\\[\\]]", "").trim())
            .collect(Collectors.toList());

        return Meta.create(metaString.get(0).trim(), metaString.get(1).trim(), metaString.get(2).trim(), algorithm);
    }

}
