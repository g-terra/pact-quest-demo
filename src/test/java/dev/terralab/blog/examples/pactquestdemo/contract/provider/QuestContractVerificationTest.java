package dev.terralab.blog.examples.pactquestdemo.contract.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.spring6.PactVerificationSpring6Provider;
import dev.terralab.blog.examples.pactquestdemo.domain.Quest;
import dev.terralab.blog.examples.pactquestdemo.domain.Status;
import dev.terralab.blog.examples.pactquestdemo.repository.QuestRepository;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("QuestManagerApi")
@PactFolder("pacts")
public class QuestContractVerificationTest {
    
    @MockitoBean
    private QuestRepository questRepository;
    
    @State({"at least one quest exists in the database"}) // default state
    public void atLeastOneQuestExists() {
        Mockito.when(questRepository.findAll()).thenReturn(List.of(
                new Quest(1L, "Quest 1", Status.NOT_STARTED, "100 XP")
        ));
    }

    @State("Quest with ID 1 does not exist in the database")
    public Map<String, Object> questWithId1DoesNotExist() {
        var id = 1L;
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.empty());
        return Map.of("id", id);
    }
    
    @State("Quest with ID 1 exists in the database")
    public Map<String, Object> questWithId1Exists() {
        var id = 1L;
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.of(new Quest(id, "Quest 1", Status.NOT_STARTED, "100 XP")));
        return Map.of("id", id);
    }
    
    @State("A quest exists with ID 1 and status IN_PROGRESS")
    public Map<String, Object> questWithId1AndStatusInProgress() {
        var id = 1L;
        var quest = new Quest(id, "Quest 1", Status.IN_PROGRESS, "100 XP");
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.of(quest));
        return Map.of("id", id);
    }
    
    @State("A quest exists with ID 1 and status NOT_STARTED")
    public Map<String, Object> questWithId1AndStatusNotStarted() {
        var id = 1L;
        var quest = new Quest(id, "Quest 1", Status.NOT_STARTED, "100 XP");
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.of(quest));
        return Map.of("id", id);
    }
    
    @State("Quest with ID 1 exists in the database and status is COMPLETED")
    public Map<String, Object> questWithId1AndStatusCompleted() {
        var id = 1L;
        var quest = new Quest(id, "Quest 1", Status.COMPLETED, "100 XP");
        Mockito.when(questRepository.findById(id)).thenReturn(Optional.of(quest));
        return Map.of("id", id);
    }
    
    @TestTemplate
    @ExtendWith(PactVerificationSpring6Provider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
