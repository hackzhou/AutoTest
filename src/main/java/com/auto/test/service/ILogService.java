package com.auto.test.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import com.auto.test.bean.SimpleJsonResult;

@Path("/log")
public interface ILogService {
	
	@GET
	@Path("/isrun")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult isRun();
	
	@GET
	@Path("/server={server}/start")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult start(@PathParam("server") String server);
	
	@GET
	@Path("/read")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult read();
	
	@GET
	@Path("/stop")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult stop();
	
}
