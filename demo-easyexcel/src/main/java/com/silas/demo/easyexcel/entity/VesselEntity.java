package com.silas.demo.easyexcel.entity;

public class VesselEntity extends BaseEntity {

    /**
     * 租户ID
     */
    private String tenantId;


    /**
     * 船ID
     */
    private long vesselId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public long getVesselId() {
        return vesselId;
    }

    public void setVesselId(long vesselId) {
        this.vesselId = vesselId;
    }
}
