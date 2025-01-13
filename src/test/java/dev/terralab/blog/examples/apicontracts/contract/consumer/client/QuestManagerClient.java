package dev.terralab.blog.examples.apicontracts.contract.consumer.client;

import dev.terralab.blog.examples.apicontracts.contract.consumer.client.base.ApiClient;
import dev.terralab.blog.examples.apicontracts.contract.consumer.client.base.ApiResponse;

public class QuestManagerClient extends ApiClient {

    public QuestManagerClient(String baseUrl) {
        super(baseUrl);
    }

    public ApiResponse<QuestResponse[]> getQuests() {
        return get("/quest", QuestResponse[].class);
    }

    public ApiResponse<QuestResponse> updateQuestStatus(String questId, String status) {
        return put("/quest/" + questId, new QuestUpdateRequest(status), QuestResponse.class);
    }

    public record QuestResponse(int id, String name, String reward, String status) {
    }

    public record QuestUpdateRequest(String status) {
    }
}




