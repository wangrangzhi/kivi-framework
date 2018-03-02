package com.kivi.framwork.actuator.mapping;

import java.util.LinkedHashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.actuate.endpoint.mvc.ActuatorMediaTypes;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMappingCustomizer;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ActuatorCustomizer implements EndpointHandlerMappingCustomizer {

	static class Fix extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			Object attribute = request.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
			if (attribute instanceof LinkedHashSet) {
				@SuppressWarnings("unchecked")
				LinkedHashSet<MediaType> lhs = (LinkedHashSet<MediaType>) attribute;
				if (lhs.remove(ActuatorMediaTypes.APPLICATION_ACTUATOR_V1_JSON)) {
					lhs.add(MediaType.APPLICATION_JSON);
				}
			}
			return true;
		}

	}

	@Override
	public void customize(EndpointHandlerMapping mapping) {
		mapping.setInterceptors(new Object[] { new Fix() });
	}

}
