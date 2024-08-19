package com.test.spring.jwtoauth.dtos.responses;

import java.util.Collection;
import java.util.List;

public class ClientListResponse extends SuccessResponse {
    private PageMeta pageMeta = null;
    private Collection<ClientSummaryDto> clients = List.of();


    public ClientListResponse(List<ClientDetailsResponse> clientResponses) {
        this.pageMeta = pageMeta;
        this.clients = clients;
    }

    public PageMeta getPageMeta() {
        return pageMeta;
    }

    public Collection<ClientSummaryDto> getClients() {
        return clients;
    }
}
