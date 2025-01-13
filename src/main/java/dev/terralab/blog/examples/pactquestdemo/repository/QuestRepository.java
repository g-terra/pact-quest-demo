package dev.terralab.blog.examples.pactquestdemo.repository;

import dev.terralab.blog.examples.pactquestdemo.domain.Quest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestRepository {
     
    Optional<Quest> findById(Long id);
    
    List<Quest> findAll();
    
    void save(Quest quest);
}
