package com.library.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class InputRequest
{
    private RequestPayload requestPayload;

    public InputRequest() {}

    public InputRequest(RequestPayload requestPayload) {
        this.requestPayload = requestPayload;
    }

    public RequestPayload getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(RequestPayload requestPayload) {
        this.requestPayload = requestPayload;
    }
}
