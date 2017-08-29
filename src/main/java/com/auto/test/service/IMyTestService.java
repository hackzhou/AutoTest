package com.auto.test.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.auto.test.bean.SimpleJsonResult;

@Path("/my")
public interface IMyTestService {
	
	@GET
	@Path("/test")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult my();

}
