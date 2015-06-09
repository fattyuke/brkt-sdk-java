package com.brkt.client;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Constants {

    public static final Type TYPE_OPERATING_SYSTEM_LIST =
            new TypeToken<ArrayList<OperatingSystem>>() {}.getType();
    public static final Type TYPE_IMAGE_DEFINITION_LIST =
            new TypeToken<ArrayList<ImageDefinition>>() {}.getType();
    public static final Type TYPE_CSP_IMAGE_LIST =
            new TypeToken<ArrayList<CspImage>>() {}.getType();
    public static final Type TYPE_MACHINE_TYPE_LIST =
            new TypeToken<ArrayList<MachineType>>() {}.getType();
    public static final Type TYPE_BILLING_GROUP_LIST =
            new TypeToken<ArrayList<BillingGroup>>() {}.getType();
    public static final Type TYPE_NETWORK_LIST =
            new TypeToken<ArrayList<Network>>() {}.getType();
    public static final Type TYPE_ZONE_LIST =
            new TypeToken<ArrayList<Zone>>() {}.getType();
    public static final Type TYPE_SECURITY_GROUP_LIST =
            new TypeToken<ArrayList<SecurityGroup>>() {}.getType();
    public static final Type TYPE_SECURITY_GROUP_RULE_LIST =
            new TypeToken<ArrayList<SecurityGroupRule>>() {}.getType();
    public static final Type TYPE_COMPUTING_CELL_LIST =
            new TypeToken<ArrayList<ComputingCell>>() {}.getType();
    public static final Type TYPE_VOLUME_LIST =
            new TypeToken<ArrayList<Volume>>() {}.getType();
    public static final Type TYPE_CLOUD_INIT_LIST =
            new TypeToken<ArrayList<CloudInit>>() {}.getType();
    public static final Type TYPE_INSTANCE_LIST =
            new TypeToken<ArrayList<Instance>>() {}.getType();
    public static final Type TYPE_WORKLOAD_LIST =
            new TypeToken<ArrayList<Workload>>() {}.getType();
    public static final Type TYPE_LOAD_BALANCER_LIST =
            new TypeToken<ArrayList<LoadBalancer>>() {}.getType();
    public static final Type TYPE_LOAD_BALANCER_LISTENER_LIST =
            new TypeToken<ArrayList<LoadBalancerListener>>() {}.getType();

    public static final String OPERATING_SYSTEM_ROOT = "/v1/api/config/operatingsystem";
    public static final String IMAGE_DEFINITION_ROOT = "/v1/api/config/imagedefinition";
    public static final String CSP_IMAGE_ROOT = "/v1/api/config/cspimage";
    public static final String MACHINE_TYPE_ROOT = "/v1/api/config/machinetype";
    public static final String BILLING_GROUP_ROOT = "/v1/api/config/billinggroup";
    public static final String NETWORK_ROOT = "/v1/api/config/network";
    public static final String ZONE_ROOT = "/v1/api/config/zone";
    public static final String SECURITY_GROUP_ROOT = "/v1/api/config/securitygroup";
    public static final String SECURITY_GROUP_RULE_ROOT = "/v1/api/config/securitygrouprule";
    public static final String COMPUTING_CELL_ROOT = "/v1/api/config/computingcell";
    public static final String VOLUME_ROOT = "/v1/api/config/brktvolume";
    public static final String CLOUD_INIT_ROOT = "/v1/api/config/cloudinit";
    public static final String INSTANCE_ROOT = "/v2/api/config/instance";
    public static final String V1_INSTANCE_ROOT = "/v1/api/config/instance";
    public static final String WORKLOAD_ROOT = "/v2/api/config/workload";
    public static final String LOAD_BALANCER_ROOT = "/v1/api/config/loadbalancer";
    public static final String LOAD_BALANCER_LISTENER_ROOT = "/v1/api/config/loadbalancer/listener";

    public enum RequestedState {
        AVAILABLE, STOPPED, UNAVAILABLE, DELETED
    }

    public enum ServiceLevelObjective {
        GOLD(1), SILVER(2), BRONZE(3), NONE(0);

        public final int id;

        ServiceLevelObjective(int id) {
            this.id = id;
        }

        public static ServiceLevelObjective getById(int id) {
            for (ServiceLevelObjective slo : ServiceLevelObjective.values()) {
                if (slo.id == id) {
                    return slo;
                }
            }
            throw new IllegalArgumentException("Invalid SLO id: " + id);
        }
    }

    public enum VolumeType {
        STANDARD(1), HIGH_PERFORMANCE(2), COST_OPTIMIZED(3);

        public final int id;

        VolumeType(int id) {
            this.id = id;
        }

        public static VolumeType getById(int id) {
            if (id == 1) {
                return STANDARD;
            }
            if (id == 2) {
                return HIGH_PERFORMANCE;
            }
            if (id == 3) {
                return COST_OPTIMIZED;
            }
            throw new IllegalArgumentException("Invalid VolumeType id: " + id);
        }
    }

    public enum Availability {
        STANDARD(1), HIGH(2);

        public final int id;

        Availability(int id) { this.id = id; }

        public static Availability getById(int id) {
            if (id == 1) {
                return STANDARD;
            }
            if (id == 2) {
                return HIGH;
            }
            throw new IllegalArgumentException("Invalid Availability id: " + id);
        }
    }

    public enum Provider {
        AWS(1), GCE(2);

        public final int id;

        Provider(int id) { this.id = id; }

        public static Provider getById(int id) {
            if (id == 1) {
                return AWS;
            }
            if (id == 2) {
                return GCE;
            }
            throw new IllegalArgumentException("Invalid Provider id: " + id);
        }
    }

    public enum RebootStatus {
        DISPATCHED, FINISHED, FAILED
    }

    public enum Protocol {
        TCP, HTTP
    }

    public enum DeploymentType {
        DEFAULT, CONFIGURED, ADVANCED
    }
}
