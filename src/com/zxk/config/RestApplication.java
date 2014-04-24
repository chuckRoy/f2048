package com.zxk.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.zxk.resources.F2048;

public class RestApplication extends ResourceConfig {
	
	public RestApplication() {
		super(F2048.class,
				MultiPartFeature.class,
				JacksonFeature.class);
	}
}


