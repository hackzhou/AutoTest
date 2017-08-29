package com.auto.test.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.auto.test.bean.SimpleJsonResult;

@Path("/web")
public interface IWebService {
	
	@GET
	@Path("/url={url}/project={project}/run")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult run(@PathParam("url") String url, @PathParam("project") String project);
	
}
