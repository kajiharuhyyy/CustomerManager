package com.kajiharuhyyy.customermanager.domain;

public enum CustomerStatus {
    NEW("新規"),
    IN_PROGRESS("対応中"),
    CONTRACTED("契約済み"),
    LOST("失注");

    private final String label;

    CustomerStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
