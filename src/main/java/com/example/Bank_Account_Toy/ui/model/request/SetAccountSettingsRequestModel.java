package com.example.Bank_Account_Toy.ui.model.request;

public class SetAccountSettingsRequestModel {
    private String userId;
    private String settingId;
    private Boolean settingValue;

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
