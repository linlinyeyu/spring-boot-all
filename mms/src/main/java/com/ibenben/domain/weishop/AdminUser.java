package com.ibenben.domain.weishop;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

public class AdminUser implements Serializable {
    private Integer userId;

    @JSONField(name="user_name")
    private String userName;

    private String realName;

    private String password;

    private String ecSalt;

    private Date addTime;

    private Date lastLogin;

    private Date lastUpdatePasswordTime;

    private String lastIp;

    private String langType;

    private Short agencyId;

    private Short suppliersId;

    private Short roleId;

    private Integer shopId;

    private String facilityIds;

    private String roleIds;

    private String shippingId;

    private String status;

    private String merchantIds;

    private Integer areaId;

    private String allowedIpType;

    private String allowedIpList;
    
    private Set<String> actionSet;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEcSalt() {
        return ecSalt;
    }

    public void setEcSalt(String ecSalt) {
        this.ecSalt = ecSalt == null ? null : ecSalt.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastUpdatePasswordTime() {
        return lastUpdatePasswordTime;
    }

    public void setLastUpdatePasswordTime(Date lastUpdatePasswordTime) {
        this.lastUpdatePasswordTime = lastUpdatePasswordTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType == null ? null : langType.trim();
    }

    public Short getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Short agencyId) {
        this.agencyId = agencyId;
    }

    public Short getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(Short suppliersId) {
        this.suppliersId = suppliersId;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getFacilityIds() {
        return facilityIds;
    }

    public void setFacilityIds(String facilityIds) {
        this.facilityIds = facilityIds == null ? null : facilityIds.trim();
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds == null ? null : roleIds.trim();
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId == null ? null : shippingId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(String merchantIds) {
        this.merchantIds = merchantIds == null ? null : merchantIds.trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAllowedIpType() {
        return allowedIpType;
    }

    public void setAllowedIpType(String allowedIpType) {
        this.allowedIpType = allowedIpType == null ? null : allowedIpType.trim();
    }

    public String getAllowedIpList() {
        return allowedIpList;
    }

    public void setAllowedIpList(String allowedIpList) {
        this.allowedIpList = allowedIpList == null ? null : allowedIpList.trim();
    }
    
    public Set<String> getActionSet() {
		return actionSet;
	}

	public void setActionSet(Set<String> actionSet) {
		this.actionSet = actionSet;
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
        AdminUser other = (AdminUser) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getEcSalt() == null ? other.getEcSalt() == null : this.getEcSalt().equals(other.getEcSalt()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getLastLogin() == null ? other.getLastLogin() == null : this.getLastLogin().equals(other.getLastLogin()))
            && (this.getLastUpdatePasswordTime() == null ? other.getLastUpdatePasswordTime() == null : this.getLastUpdatePasswordTime().equals(other.getLastUpdatePasswordTime()))
            && (this.getLastIp() == null ? other.getLastIp() == null : this.getLastIp().equals(other.getLastIp()))
            && (this.getLangType() == null ? other.getLangType() == null : this.getLangType().equals(other.getLangType()))
            && (this.getAgencyId() == null ? other.getAgencyId() == null : this.getAgencyId().equals(other.getAgencyId()))
            && (this.getSuppliersId() == null ? other.getSuppliersId() == null : this.getSuppliersId().equals(other.getSuppliersId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getShopId() == null ? other.getShopId() == null : this.getShopId().equals(other.getShopId()))
            && (this.getFacilityIds() == null ? other.getFacilityIds() == null : this.getFacilityIds().equals(other.getFacilityIds()))
            && (this.getRoleIds() == null ? other.getRoleIds() == null : this.getRoleIds().equals(other.getRoleIds()))
            && (this.getShippingId() == null ? other.getShippingId() == null : this.getShippingId().equals(other.getShippingId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getMerchantIds() == null ? other.getMerchantIds() == null : this.getMerchantIds().equals(other.getMerchantIds()))
            && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
            && (this.getAllowedIpType() == null ? other.getAllowedIpType() == null : this.getAllowedIpType().equals(other.getAllowedIpType()))
            && (this.getAllowedIpList() == null ? other.getAllowedIpList() == null : this.getAllowedIpList().equals(other.getAllowedIpList()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEcSalt() == null) ? 0 : getEcSalt().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getLastLogin() == null) ? 0 : getLastLogin().hashCode());
        result = prime * result + ((getLastUpdatePasswordTime() == null) ? 0 : getLastUpdatePasswordTime().hashCode());
        result = prime * result + ((getLastIp() == null) ? 0 : getLastIp().hashCode());
        result = prime * result + ((getLangType() == null) ? 0 : getLangType().hashCode());
        result = prime * result + ((getAgencyId() == null) ? 0 : getAgencyId().hashCode());
        result = prime * result + ((getSuppliersId() == null) ? 0 : getSuppliersId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getShopId() == null) ? 0 : getShopId().hashCode());
        result = prime * result + ((getFacilityIds() == null) ? 0 : getFacilityIds().hashCode());
        result = prime * result + ((getRoleIds() == null) ? 0 : getRoleIds().hashCode());
        result = prime * result + ((getShippingId() == null) ? 0 : getShippingId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMerchantIds() == null) ? 0 : getMerchantIds().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getAllowedIpType() == null) ? 0 : getAllowedIpType().hashCode());
        result = prime * result + ((getAllowedIpList() == null) ? 0 : getAllowedIpList().hashCode());
        return result;
    }
}