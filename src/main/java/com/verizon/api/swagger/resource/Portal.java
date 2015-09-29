package com.verizon.api.swagger.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class Portal extends Application {
	Set<Class<?>> resources = new HashSet<Class<?>>();

	public Portal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<Class<?>> getClasses() {
		resources.add(HelloWebapp.class);
		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		return resources;
	}

}
