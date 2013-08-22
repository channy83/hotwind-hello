package kr.co.inger.hotwind.hello.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import kr.co.inger.hotwind.hello.resources.file_hello.FileEntry;
import kr.co.inger.hotwind.jaxrs.JaxRsResource;
import kr.co.inger.hotwind.jcs.InjectJcs;
import kr.co.inger.hotwind.request_check.session.AccessTokenedSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.jcs.JCS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.servlet.RequestScoped;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * 테스트용 "파일 업로드-다운로드 테스트" 리소스.
 * 
 * @author jhyun
 * @since 2013-Aug-14-Wed
 * 
 */
@JaxRsResource
@Path("/file-hello")
@RequestScoped
public class FileHelloResource {

	private Logger logger = LoggerFactory.getLogger(FileHelloResource.class);

	@InjectJcs(cacheName = "TmpFileStore")
	private JCS tmpFileCache;

	@GET
	@Path("/uploadForm")
	@Produces(MediaType.TEXT_HTML)
	public Viewable uploadForm() {
		return new Viewable("/file-hello/uploadForm.jsp",
				new ImmutableMap.Builder<String, Object>().build());
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String upload(@FormDataParam("file") final InputStream fileStream,
			@FormDataParam("file") final FormDataContentDisposition fileDetail)
			throws IOException, Exception {
		// 업로드된 내용을 FileEntry으로 만들기.
		FileEntry e = uploadIntoFileEntry(fileStream, fileDetail);
		//
		final String uuid = UUID.randomUUID().toString();
		tmpFileCache.putSafe(uuid, e);
		//
		return String.format("file-id=[%s] file=[%s]", uuid,
				ObjectUtils.toString(e));
	}

	private FileEntry uploadIntoFileEntry(final InputStream fileStream,
			final FormDataContentDisposition fileDetail) throws IOException {
		FileEntry e = new FileEntry();
		// FormDataContentDisposition에서 다른 정보는 별로 의미가 없음. 파일 이름만 사용.
		e.setFilename(fileDetail.getFileName());
		//
		byte[] bs = IOUtils.toByteArray(fileStream);
		e.setFilebytes(bs);
		e.setFilesize(bs.length);
		return e;
	}

	@GET
	@Path("/download/{uuid}")
	public Response download(@PathParam("uuid") final String uuid) {
		FileEntry e = (FileEntry) tmpFileCache.get(uuid);
		if (null == e) {
			return Response.status(Status.NOT_FOUND)
					.entity(String.format("FILE NOT FOUND [%s]", uuid)).build();
		}
		// FIXME: file-size?
		ResponseBuilder response = Response.ok((Object) e.getFilebytes());
		// guess mime-type? javax.activation.
		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
		response.type(mimetypesFileTypeMap.getContentType(e.getFilename()));
		// SEE: http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html
		response.header("Content-Disposition",
				String.format("inline; filename=\"%s\"", e.getFilename()));
		//
		return response.build();
	}
	
	@GET
	@Path("/foo")
	@Produces(MediaType.TEXT_PLAIN) 
	public String foo(@Context AccessTokenedSession v) {
		return "foobar!";
	}
}
