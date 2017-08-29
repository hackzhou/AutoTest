package com.auto.test.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.auto.test.bean.SimpleJsonResult;

@Path("/server")
public interface IServerService {
	
	@GET
	@Path("/server={server}/start")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult start(@PathParam("server") String server);
	
	@GET
	@Path("/server={server}/stop")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult stop(@PathParam("server") String server);
	
	@GET
	@Path("/server={server}/kill")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult kill(@PathParam("server") String server);

}
