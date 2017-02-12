package com.ibenben.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class EfficiencyCoefficient implements Serializable {
	@JSONField(name="efficiency_coefficient_id")
    private Integer efficiencyCoefficientId;

	@JSONField(name="merchant_id")
    private Integer merchantId;

	@JSONField(name="facility_id")
    private Integer facilityId;

	@JSONField(name="effciency_coefficient_apply_id")
    private Integer efficiencyCoefficientApplyId;

	@JSONField(name="inventory_management_coff")
    private BigDecimal inventoryManagementCoff;

	@JSONField(name="document_management_coff")
    private BigDecimal documentManagementCoff;

	@JSONField(name="distribution_shipping_coff")
    private BigDecimal distributionShippingCoff;

	@JSONField(name="start_date")
    private Date startDate;

	@JSONField(name="end_date")
    private Date endDate;

	@JSONField(name="start_user")
    private String startUser;

	@JSONField(name="end_user")
    private String endUser;

	@JSONField(name="is_now")
    private Byte isNow;

    private String note;

    @JSONField(name="merchant_name")
    private String merchantName;
    
    @JSONField(name="facility_name")
    private String facilityName;
    
    private static final long serialVersionUID = 1L;

    public Integer getEfficiencyCoefficientId() {
        return efficiencyCoefficientId;
    }

    public void setEfficiencyCoefficientId(Integer efficiencyCoefficientId) {
        this.efficiencyCoefficientId = efficiencyCoefficientId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Integer getEfficiencyCoefficientApplyId() {
        return efficiencyCoefficientApplyId;
    }

    public void setEfficiencyCoefficientApplyId(Integer efficiencyCoefficientApplyId) {
        this.efficiencyCoefficientApplyId = efficiencyCoefficientApplyId;
    }

    public BigDecimal getInventoryManagementCoff() {
        return inventoryManagementCoff;
    }

    public void setInventoryManagementCoff(BigDecimal inventoryManagementCoff) {
        this.inventoryManagementCoff = inventoryManagementCoff;
    }

    public BigDecimal getDocumentManagementCoff() {
        return documentManagementCoff;
    }

    public void setDocumentManagementCoff(BigDecimal documentManagementCoff) {
        this.documentManagementCoff = documentManagementCoff;
    }

    public BigDecimal getDistributionShippingCoff() {
        return distributionShippingCoff;
    }

    public void setDistributionShippingCoff(BigDecimal distributionShippingCoff) {
        this.distributionShippingCoff = distributionShippingCoff;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartUser() {
        return startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser == null ? null : startUser.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public Byte getIsNow() {
        return isNow;
    }

    public void setIsNow(Byte isNow) {
        this.isNow = isNow;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EfficiencyCoefficient other = (EfficiencyCoefficient) that;
        return (this.getEfficiencyCoefficientId() == null ? other.getEfficiencyCoefficientId() == null : this.getEfficiencyCoefficientId().equals(other.getEfficiencyCoefficientId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getFacilityId() == null ? other.getFacilityId() == null : this.getFacilityId().equals(other.getFacilityId()))
            && (this.getEfficiencyCoefficientApplyId() == null ? other.getEfficiencyCoefficientApplyId() == null : this.getEfficiencyCoefficientApplyId().equals(other.getEfficiencyCoefficientApplyId()))
            && (this.getInventoryManagementCoff() == null ? other.getInventoryManagementCoff() == null : this.getInventoryManagementCoff().equals(other.getInventoryManagementCoff()))
            && (this.getDocumentManagementCoff() == null ? other.getDocumentManagementCoff() == null : this.getDocumentManagementCoff().equals(other.getDocumentManagementCoff()))
            && (this.getDistributionShippingCoff() == null ? other.getDistributionShippingCoff() == null : this.getDistributionShippingCoff().equals(other.getDistributionShippingCoff()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getStartUser() == null ? other.getStartUser() == null : this.getStartUser().equals(other.getStartUser()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getIsNow() == null ? other.getIsNow() == null : this.getIsNow().equals(other.getIsNow()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEfficiencyCoefficientId() == null) ? 0 : getEfficiencyCoefficientId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getFacilityId() == null) ? 0 : getFacilityId().hashCode());
        result = prime * result + ((getEfficiencyCoefficientApplyId() == null) ? 0 : getEfficiencyCoefficientApplyId().hashCode());
        result = prime * result + ((getInventoryManagementCoff() == null) ? 0 : getInventoryManagementCoff().hashCode());
        result = prime * result + ((getDocumentManagementCoff() == null) ? 0 : getDocumentManagementCoff().hashCode());
        result = prime * result + ((getDistributionShippingCoff() == null) ? 0 : getDistributionShippingCoff().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getStartUser() == null) ? 0 : getStartUser().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getIsNow() == null) ? 0 : getIsNow().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        return result;
    }
}