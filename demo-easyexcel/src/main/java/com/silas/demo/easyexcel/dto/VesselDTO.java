package com.silas.demo.easyexcel.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class VesselDTO {
    @JsonSerialize(
        using = ToStringSerializer.class
    )
    private Long id;
    @JsonSerialize(
        using = ToStringSerializer.class
    )
    private Long vesselId;
    private String tenantId;

    public VesselDTO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVesselId() {
        return this.vesselId;
    }

    public void setVesselId(Long vesselId) {
        this.vesselId = vesselId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
