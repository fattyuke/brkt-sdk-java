package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a volume.
 */
public class VolumeRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public VolumeRequestBuilder autoSnapshotDurationDays(int days) {
        reqBuilder.attr("auto_snapshot_duration_days", days);
        return this;
    }

    public VolumeRequestBuilder availability(Constants.Availability availability) {
        reqBuilder.attr("availability", availability.id);
        return this;
    }

    public VolumeRequestBuilder billingGroupId(String id) {
        reqBuilder.attr("billing_group", id);
        return this;
    }

    public VolumeRequestBuilder volumeTemplateId(String id) {
        reqBuilder.attr("bracket_volume_template", id);
        return this;
    }

    public VolumeRequestBuilder computingCellId(String id) {
        reqBuilder.attr("computing_cell", id);
        return this;
    }

    public VolumeRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public VolumeRequestBuilder instanceId(String id) {
        reqBuilder.attr("instance", id);
        return this;
    }

    public VolumeRequestBuilder iops(int iops) {
        reqBuilder.attr("iops", iops);
        return this;
    }

    public VolumeRequestBuilder iopsMax(int iopsMax) {
        reqBuilder.attr("iops_max", iopsMax);
        return this;
    }

    public VolumeRequestBuilder largeIo(boolean largeIo) {
        reqBuilder.attr("large_io", largeIo);
        return this;
    }

    public VolumeRequestBuilder leaseExpireTime(Timestamp ts) {
        reqBuilder.attr("lease_expire_time", ts);
        return this;
    }

    public VolumeRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public VolumeRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public VolumeRequestBuilder requestedState(Constants.RequestedState state) {
        reqBuilder.attr("requested_state", state);
        return this;
    }

    public VolumeRequestBuilder sizeInGb(int size) {
        reqBuilder.attr("size_in_gb", size);
        return this;
    }

    public VolumeRequestBuilder slo(Constants.ServiceLevelObjective slo) {
        reqBuilder.attr("slo", slo.id);
        return this;
    }

    public VolumeRequestBuilder volumeType(Constants.VolumeType type) {
        reqBuilder.attr("volume_type", type.id);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     *
     * @throws com.brkt.client.util.RequestBuilder.MissingFieldsError
     * if any required fields are missing
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
