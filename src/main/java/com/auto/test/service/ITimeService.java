package com.auto.test.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import com.auto.test.bean.SimpleJsonResult;

@Path("/datetime")
public interface ITimeService {
	
	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult getDateTime();
	
	@GET
	@Path("/set/time={time}")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult setDateTime(@PathParam("time") String time);
	
}
