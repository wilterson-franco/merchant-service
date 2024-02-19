package com.wilterson.cms.common.validation;

import static com.wilterson.cms.common.validation.IssueFactory.ReasonCode.INVALID_DATA;

public enum IssueFactory {

    MERCHANT_NAME(INVALID_DATA.getValue() + "merchant.name"),
    MERCHANT_GUID(INVALID_DATA.getValue() + "merchant.guid"),
    MERCHANT_LOCATIONS(INVALID_DATA.getValue() + "merchant.locationCommands[]"),
    MERCHANT_LOCATIONS_DEFAULT(INVALID_DATA.getValue() + "merchant.locationCommands[].isDefault");

    private String reasonCode;
    public static final String MERCHANT_SERVICE = "Merchant Service";

    IssueFactory(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    enum ReasonCode {

        INVALID_DATA("invalid data: "),

        SERVER_ERROR("server.error");

        private final String value;

        ReasonCode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Issue issue(String reasonCode, String errorDescription, boolean recoverable) {
        return Issue.builder()
                .source(MERCHANT_SERVICE)
                .reasonCode(reasonCode)
                .description(errorDescription)
                .recoverable(recoverable)
                .build();
    }
}
