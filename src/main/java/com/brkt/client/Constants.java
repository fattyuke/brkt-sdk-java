package com.brkt.client;

public class Constants {

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
}
