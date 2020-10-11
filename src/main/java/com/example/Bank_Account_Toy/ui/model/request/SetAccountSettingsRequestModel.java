package com.example.Bank_Account_Toy.ui.model.request;

import static java.util.Objects.isNull;

public class SetAccountSettingsRequestModel {
    private String userId = "";
    private String settingId = "";
    private Boolean settingValue;

    public Boolean validator() {
        return nullOrEmpty(settingId) ||
                isNull(settingValue) ||
                nullOrEmpty(userId);
    }

    public Boolean nullOrEmpty(String prop) {
        return (isNull(prop) || prop.isEmpty());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public Boolean getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(Boolean settingValue) {
        this.settingValue = settingValue;
    }
}
