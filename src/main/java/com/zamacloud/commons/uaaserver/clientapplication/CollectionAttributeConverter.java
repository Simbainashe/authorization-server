package com.zamacloud.commons.uaaserver.clientapplication;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Fact S Musingarimi
 * 11/1/18
 * 10:16 PM
 */

public class CollectionAttributeConverter implements AttributeConverter<Collection<String>, String> {
    @Override
    public String convertToDatabaseColumn(Collection<String> strings) {
        if (Objects.isNull(strings) || strings.isEmpty()) {
            return null;
        }
        return String.join(",", strings);
    }

    @Override
    public Collection<String> convertToEntityAttribute(String commaSeparatedValues) {
        if (Objects.isNull(commaSeparatedValues)) {
            return Collections.EMPTY_LIST;
        }
        String[] values = commaSeparatedValues.split(",");
        return Arrays.asList(values);
    }
}
