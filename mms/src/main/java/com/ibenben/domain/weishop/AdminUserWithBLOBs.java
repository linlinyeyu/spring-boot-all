package com.ibenben.domain.weishop;

import java.io.Serializable;

public class AdminUserWithBLOBs extends AdminUser implements Serializable {
    private String actionList;

    private String navList;

    private String todolist;

    private static final long serialVersionUID = 1L;

    public String getActionList() {
        return actionList;
    }

    public void setActionList(String actionList) {
        this.actionList = actionList == null ? null : actionList.trim();
    }

    public String getNavList() {
        return navList;
    }

    public void setNavList(String navList) {
        this.navList = navList == null ? null : navList.trim();
    }

    public String getTodolist() {
        return todolist;
    }

    public void setTodolist(String todolist) {
        this.todolist = todolist == null ? null : todolist.trim();
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
        AdminUserWithBLOBs other = (AdminUserWithBLOBs) that;
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
            && (this.getAllowedIpList() == null ? other.getAllowedIpList() == null : this.getAllowedIpList().equals(other.getAllowedIpList()))
            && (this.getActionList() == null ? other.getActionList() == null : this.getActionList().equals(other.getActionList()))
            && (this.getNavList() == null ? other.getNavList() == null : this.getNavList().equals(other.getNavList()))
            && (this.getTodolist() == null ? other.getTodolist() == null : this.getTodolist().equals(other.getTodolist()));
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
        result = prime * result + ((getActionList() == null) ? 0 : getActionList().hashCode());
        result = prime * result + ((getNavList() == null) ? 0 : getNavList().hashCode());
        result = prime * result + ((getTodolist() == null) ? 0 : getTodolist().hashCode());
        return result;
    }
}