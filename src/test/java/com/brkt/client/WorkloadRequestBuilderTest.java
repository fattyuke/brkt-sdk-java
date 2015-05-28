package com.brkt.client;

import com.brkt.client.util.RequestBuilder;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WorkloadRequestBuilderTest {

    @Test(expected = RequestBuilder.MissingFieldsError.class)
    public void testMissingField() {
        WorkloadRequestBuilder builder = WorkloadRequestBuilder.newCreateRequest();
        builder.billingGroupId("1").zoneId("2");
        builder.build();
    }

    @Test
    public void testNoMissingField() {
        WorkloadRequestBuilder builder = WorkloadRequestBuilder.newUpdateRequest();
        builder.billingGroupId("1").zoneId("2");
        builder.build();
    }

    @Test
    public void testRequest() {
        WorkloadRequestBuilder builder = WorkloadRequestBuilder.newUpdateRequest();
        builder.name("New name").requestedState(Constants.RequestedState.DELETED);
        Map<String, Object> attrs = builder.build();

        assertEquals(2, attrs.size());
        assertEquals("New name", attrs.get("name"));
        assertEquals(Constants.RequestedState.DELETED, attrs.get("requested_state"));
    }
}
