package dev.terralab.blog.examples.pactquestdemo.controller;

import dev.terralab.blog.examples.pactquestdemo.dto.QuestResponseDto;
import dev.terralab.blog.examples.pactquestdemo.dto.QuestUpdateRequestDto;
import dev.terralab.blog.examples.pactquestdemo.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @GetMapping(path = "/quest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuestResponseDto>> getQuests() {
        List<QuestResponseDto> quests = questService.getQuests();
        return ResponseEntity.ok(quests);
    }

    @PutMapping(path = "/quest/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestResponseDto> putQuest(@PathVariable Long id, @RequestBody QuestUpdateRequestDto requestDto) {
        QuestResponseDto questResponseDto = questService.updateQuestStatus(id, requestDto);
        return ResponseEntity.ok(questResponseDto);
    }

}
