package dev.terralab.blog.examples.pactquestdemo.service;

import dev.terralab.blog.examples.pactquestdemo.domain.Status;
import dev.terralab.blog.examples.pactquestdemo.dto.QuestResponseDto;
import dev.terralab.blog.examples.pactquestdemo.dto.QuestUpdateRequestDto;
import dev.terralab.blog.examples.pactquestdemo.exception.InvalidStatusTransitionException;
import dev.terralab.blog.examples.pactquestdemo.exception.QuestNotFoundException;
import dev.terralab.blog.examples.pactquestdemo.repository.QuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;

    public List<QuestResponseDto> getQuests() {
        return questRepository.findAll().stream().map(QuestResponseDto::from).toList();
    }

    public QuestResponseDto updateQuestStatus(Long id, QuestUpdateRequestDto quest) {

        return questRepository.findById(id)
                .map(q -> {
                    verifyStatusTransition(q.getStatus(), Status.from(quest.status()));
                    q.setStatus(Status.from(quest.status()));
                    questRepository.save(q);
                    return QuestResponseDto.from(q);
                })
                .orElseThrow(
                        () -> new QuestNotFoundException("Quest not found")
                );
    }

    private void verifyStatusTransition(Status currentStatus, Status newStatus) {
        if (currentStatus == Status.COMPLETED) {
            throw new InvalidStatusTransitionException("Quest is already completed");
        }

        if (currentStatus == Status.NOT_STARTED && newStatus == Status.IN_PROGRESS) {
            return;
        }

        if (currentStatus == Status.IN_PROGRESS && newStatus == Status.COMPLETED) {
            return;
        }

        throw new InvalidStatusTransitionException("Invalid status transition");
    }

}
