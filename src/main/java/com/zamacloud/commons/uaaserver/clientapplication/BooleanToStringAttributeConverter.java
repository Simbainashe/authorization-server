package com.zamacloud.commons.uaaserver.clientapplication;

import javax.persistence.AttributeConverter;
import java.util.Objects;

/**
 * @author Fact S Musingarimi
 * 11/2/18
 * 5:09 AM
 */

public class BooleanToStringAttributeConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean autoApprove) {
        if (Objects.isNull(autoApprove)) {
            return null;
        }
        return autoApprove.toString();
    }

    @Override
    public Boolean convertToEntityAttribute(String autoApproveStringValue) {
        if (Objects.isNull(autoApproveStringValue)) {
            return null;
        }
        return Boolean.valueOf(autoApproveStringValue);
    }
}
