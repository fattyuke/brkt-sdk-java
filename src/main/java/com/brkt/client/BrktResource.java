package com.brkt.client;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Contains fields that are common to all Bracket resources.
 */
abstract public class BrktResource {
    private String id;
    private String customer;
    private String name;
    private String description;
    private String createdBy;
    private String modifiedBy;
    private Timestamp createdTime;
    private Timestamp modifiedTime;


    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public String getCustomerId() {
        return customer;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
