package com.auto.test.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import com.auto.test.bean.SimpleJsonResult;

@Path("/file")
public interface IFileService {
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult listProjects();
	
	@GET
	@Path("/web/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult listWebProjects();
	
	@POST
    @Path("/server={server}/upload")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
    public SimpleJsonResult uploadFile(@Multipart("file")Attachment file, @PathParam("server") String server);
	
	@GET
	@Path("/server={server}/project={project}/delete")
	@Produces({ MediaType.APPLICATION_JSON })
	public SimpleJsonResult deleteFile(@PathParam("server") String server, @PathParam("project") String project);
	
}
