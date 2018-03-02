package com.kivi.framework.web.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;

import springfox.documentation.spring.web.json.Json;

/**
 * fastjson配置类
 *
 */
@Configuration
@ConditionalOnClass({ FastJsonHttpMessageConverter4.class })
@ConditionalOnProperty(name = {
		"spring.http.converters.preferred-json-mapper" }, havingValue = "fastjson", matchIfMissing = true)
@ConditionalOnMissingClass({ "springfox.documentation.spring.web.json.Json" })
public class FastJsonHttpMessageConverterConfiguration {

	protected FastJsonHttpMessageConverterConfiguration() {

	}

	@Bean
	@ConditionalOnMissingBean({ FastJsonHttpMessageConverter4.class })
	public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4() {
		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();

		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.parseMediaType("text/html;charset=UTF-8"));
		supportedMediaTypes.add(MediaType.parseMediaType("application/json"));

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.DisableCircularReferenceDetect);
		fastJsonConfig.getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);

		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		ValueFilter valueFilter = new ValueFilter() {
			public Object process(Object o, String s, Object o1) {
				if (null == o1) {
					o1 = "";
				}
				return o1;
			}
		};
		fastJsonConfig.setSerializeFilters(valueFilter);
		converter.setFastJsonConfig(fastJsonConfig);
		converter.setSupportedMediaTypes(supportedMediaTypes);

		return converter;
	}
}
