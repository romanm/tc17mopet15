package org.tc17.m15.web;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
/*
	public Converter<Info1, String> getInfo1ToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.tc17.m15.domain.Info1, java.lang.String>() {
            public String convert(Info1 info1) {
                return new StringBuilder().append(info1.getInfoName()).toString();
            }
        };
    }

	public Converter<Long, Info1> getIdToInfo1Converter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.tc17.m15.domain.Info1>() {
            public org.tc17.m15.domain.Info1 convert(java.lang.Long id) {
                return Info1.findInfo1(id);
            }
        };
    }

	public Converter<String, Info1> getStringToInfo1Converter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.tc17.m15.domain.Info1>() {
            public org.tc17.m15.domain.Info1 convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Info1.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getInfo1ToStringConverter());
        registry.addConverter(getIdToInfo1Converter());
        registry.addConverter(getStringToInfo1Converter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
 */
}
