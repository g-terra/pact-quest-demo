package dev.terralab.blog.examples.pactquestdemo.dto;

import dev.terralab.blog.examples.pactquestdemo.domain.Quest;

public record QuestResponseDto(Long id, String name, String status, String reward){
    
    public static QuestResponseDto from(Quest quest) {
        return new QuestResponseDto(quest.getId(), quest.getName(), quest.getStatus().name(), quest.getReward());
    }
}
