package dev.terralab.blog.examples.apicontracts.contract.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import dev.terralab.blog.examples.apicontracts.contract.consumer.client.QuestManagerClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@PactConsumerTest
public class QuestManagerApiConsumerPactTest extends QuestManagerApiConsumerPactBaseTest {

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForGetQuests")
    void should_respond_with_quests(MockServer mockServer) {
        var questManagerClient = new QuestManagerClient(mockServer.getUrl());
        var quests = questManagerClient.getQuests();
        assertThat(quests.success()).isTrue();
        assertThat(quests.data()).hasSizeGreaterThan(0);
        assertThat(quests.data()).allMatch(quest ->
                List.of("NOT_STARTED", "IN_PROGRESS", "COMPLETED").contains(quest.status())
        );
    }

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForUpdatingAQuestFromNotStartedToInProgress")
    void should_respond_with_updated_quest_from_not_started_to_in_progress(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "IN_PROGRESS");
        assertThat(response.success()).isTrue();
        assertThat(response.data().id()).isEqualTo(1);
        assertThat(response.data().status()).isEqualTo("IN_PROGRESS");
    }

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForUpdatingAQuestFromInProgressToComplete")
    void should_respond_with_updated_quest_from_in_progress_to_complete(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "COMPLETED");
        assertThat(response.success()).isTrue();
        assertThat(response.data().id()).isEqualTo(1);
    }

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForPutQuestNotFound")
    void should_respond_with_error_when_quest_not_found(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "IN_PROGRESS");
        assertThat(response.success()).isFalse();
        assertThat(response.error().message()).isNotEmpty();
        assertThat(response.error().code()).isEqualTo("NOT_FOUND");
    }

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForPutQuestWithInvalidStatus")
    void should_respond_with_error_when_quest_status_invalid(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "INVALID_STATUS");
        assertThat(response.success()).isFalse();
        assertThat(response.error().message()).isNotEmpty();
        assertThat(response.error().code()).isEqualTo("BAD_REQUEST");
    }

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForFailWhenTryingToRevertQuestStateFromCompleteToInProgress")
    void should_respond_with_error_when_trying_to_revert_quest_state_from_complete_to_in_progress(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "IN_PROGRESS");
        assertThat(response.success()).isFalse();
        assertThat(response.error().message()).isNotEmpty();
        assertThat(response.error().code()).isEqualTo("INVALID_ACTION");
    }

    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForFailWhenTryingToRevertQuestStateFromInProgressToNotStarted")
    void should_respond_with_error_when_trying_to_revert_quest_state_from_in_progress_to_not_started(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "NOT_STARTED");
        assertThat(response.success()).isFalse();
        assertThat(response.error().message()).isNotEmpty();
        assertThat(response.error().code()).isEqualTo("INVALID_ACTION");
    }
    
    @Test
    @PactTestFor(providerName = "QuestManagerApi", pactMethod = "createPactForFailWhenTryingToRevertQuestStateFromCompletedToNotStarted")
    void should_respond_with_error_when_trying_to_revert_quest_state_from_completed_to_not_started(MockServer mockServer) {
        var questManagerApiClient = new QuestManagerClient(mockServer.getUrl());
        var response = questManagerApiClient.updateQuestStatus("1", "NOT_STARTED");
        assertThat(response.success()).isFalse();
        assertThat(response.error().message()).isNotEmpty();
        assertThat(response.error().code()).isEqualTo("INVALID_ACTION");
    }

}
