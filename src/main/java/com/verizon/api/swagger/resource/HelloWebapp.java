package com.verizon.api.swagger.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/hello")
@Api("/hello")
public class HelloWebapp {
	private static HelloWorldService helloWorldService = new HelloWorldService();

	@GET()
	@ApiOperation(value = "Finds Pets by status", notes = "Multiple status values can be provided with comma seperated strings", response = String.class)
	public String hello() {
		return helloWorldService.sayHello();
	}
}
