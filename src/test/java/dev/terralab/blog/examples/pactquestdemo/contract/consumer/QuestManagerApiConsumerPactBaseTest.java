package dev.terralab.blog.examples.pactquestdemo.contract.consumer;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArray;
import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;

public class QuestManagerApiConsumerPactBaseTest {

    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForGetQuests(PactDslWithProvider builder) {
        return builder.given("at least one quest exists in the database")
                .uponReceiving("A GET request to /quest endpoint")
                .path("/quest")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonArray((q) -> q.object((quest) -> {
                    quest.numberType("id", 1);
                    quest.stringType("name");
                    quest.stringType("reward");
                    quest.stringMatcher("status", "NOT_STARTED|IN_PROGRESS|COMPLETED");
                })).build())
                .toPact(V4Pact.class);
    }

    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForUpdatingAQuestFromNotStartedToInProgress(PactDslWithProvider builder) {
        return builder.given("A quest exists with ID 1 and status NOT_STARTED")
                .uponReceiving("A PUT request to /quest/{:id} endpoint with IN_PROGRESS status")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringMatcher("status", "IN_PROGRESS");
                }).build())
                .willRespondWith()
                .status(200)
                .body(newJsonBody((quest) -> {
                    quest.numberType("id", 1);
                    quest.stringType("name");
                    quest.stringType("reward");
                    quest.stringMatcher("status", "IN_PROGRESS");
                }).build())
                .toPact(V4Pact.class);
    }

    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForUpdatingAQuestFromInProgressToComplete(PactDslWithProvider builder) {
        return builder.given("A quest exists with ID 1 and status IN_PROGRESS")
                .uponReceiving("A PUT request to /quest/{:id} endpoint with COMPLETED status")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringMatcher("status", "COMPLETED");
                }).build())
                .willRespondWith()
                .status(200)
                .body(newJsonBody((quest) -> {
                    quest.numberType("id", 1);
                    quest.stringType("name");
                    quest.stringType("reward");
                    quest.stringMatcher("status", "COMPLETED");
                }).build())
                .toPact(V4Pact.class);
    }

    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForPutQuestNotFound(PactDslWithProvider builder) {
        return builder
                .given("Quest with ID 1 does not exist in the database")
                .uponReceiving("A PUT request for a non-existent quest")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringType("status", "COMPLETED");
                }).build())
                .willRespondWith()
                .status(404)
                .body(newJsonBody((error) -> {
                    error.stringMatcher("code", "NOT_FOUND");
                    error.stringType("message", "Quest not found");
                }).build())
                .toPact(V4Pact.class);
    }

    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForPutQuestWithInvalidStatus(PactDslWithProvider builder) {
        return builder
                .given("Quest with ID 1 exists in the database")
                .uponReceiving("A PUT request for a quest with invalid status")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringMatcher("status" , "^(?!(?:NOT_STARTED|IN_PROGRESS|COMPLETED)$).+$");
                }).build())
                .willRespondWith()
                .status(400)
                .body(newJsonBody((error) -> {
                    error.stringMatcher("code", "BAD_REQUEST");
                    error.stringType("message", "Status must be one of NOT_STARTED, IN_PROGRESS, COMPLETED");
                }).build())
                .toPact(V4Pact.class);
    }
    
    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForFailWhenTryingToRevertQuestStateFromCompleteToInProgress(PactDslWithProvider builder) {
        return builder
                .given("Quest with ID 1 exists in the database and status is COMPLETED")
                .uponReceiving("A PUT request to revert a quest status from COMPLETED to IN_PROGRESS")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringMatcher("status", "IN_PROGRESS");
                }).build())
                .willRespondWith()
                .status(400)
                .body(newJsonBody((error) -> {
                    error.stringMatcher("code", "INVALID_ACTION");
                    error.stringType("message", "Cannot revert quest status from COMPLETED to IN_PROGRESS");
                }).build())
                .toPact(V4Pact.class);
        }

    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForFailWhenTryingToRevertQuestStateFromInProgressToNotStarted(PactDslWithProvider builder) {
        return builder
                .given("Quest with ID 1 exists in the database and status is COMPLETED")
                .uponReceiving("A PUT request to revert a quest status from IN_PROGRESS to NOT_STARTED")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringMatcher("status", "NOT_STARTED");
                }).build())
                .willRespondWith()
                .status(400)
                .body(newJsonBody((error) -> {
                    error.stringMatcher("code", "INVALID_ACTION");
                    error.stringType("message", "Cannot revert quest status from IN_PROGRESS to NOT_STARTED");
                }).build())
                .toPact(V4Pact.class);
    }


    @Pact(provider = "QuestManagerApi", consumer = "QuestManagerApiClient")
    public V4Pact createPactForFailWhenTryingToRevertQuestStateFromCompletedToNotStarted(PactDslWithProvider builder) {
        return builder
                .given("Quest with ID 1 exists in the database and status is COMPLETED")
                .uponReceiving("A PUT request to revert a quest status from COMPLETED to NOT_STARTED")
                .pathFromProviderState("/quest/${id}", "/quest/1")
                .method("PUT")
                .headers("Content-Type", "application/json")
                .body(newJsonBody((q) -> {
                    q.stringMatcher("status", "NOT_STARTED");
                }).build())
                .willRespondWith()
                .status(400)
                .body(newJsonBody((error) -> {
                    error.stringMatcher("code", "INVALID_ACTION");
                    error.stringType("message", "Cannot revert quest status from COMPLETED to NOT_STARTED");
                }).build())
                .toPact(V4Pact.class);
    }

}
