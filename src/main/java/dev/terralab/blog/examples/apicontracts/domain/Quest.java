package dev.terralab.blog.examples.apicontracts.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quest{
    private Long id;
    private String name;
    private Status status;
    private String reward;
}
