package com.demo.accountservice.converter;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDate localDate) {
		return (localDate == null ? null : Timestamp.valueOf(localDate.atStartOfDay()));
	}
	
	@Override
	public LocalDate convertToEntityAttribute(Timestamp sqlDate) {
		return (sqlDate == null ? null : sqlDate.toLocalDateTime().toLocalDate());
	}
}
