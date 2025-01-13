package dev.terralab.blog.examples.apicontracts.dto;

import dev.terralab.blog.examples.apicontracts.domain.Quest;
import dev.terralab.blog.examples.apicontracts.domain.Status;

public record QuestResponseDto(Long id, String name, String status, String reward){
    
    public static QuestResponseDto from(Quest quest) {
        return new QuestResponseDto(quest.getId(), quest.getName(), quest.getStatus().name(), quest.getReward());
    }
}
