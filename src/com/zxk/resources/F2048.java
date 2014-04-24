package com.zxk.resources;

import game.PlayingField;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import Run.Tree;

import com.zxk.entity.Cell;
import com.zxk.parse.CellParse;

@Path("/f2048")
public class F2048 {
	
	private static Logger logger = LogManager.getLogger(F2048.class);
	
	@XmlRootElement
	class MyJaxbBean {
		public String name;
		public int age;
		public Date date;
		public MyJaxbBean() {} // JAXB needs this
		public MyJaxbBean(String name, int age) {
			this.name = name;
			this.age = age;
			this.date = new Date();
		}
	}
	@Path("/cell/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String getCell(String json) {
//		logger.info("cell json = " + json);
		int direction = -1;
		try {
			int level=3;
			Cell cell = CellParse.getCell(json);
			int[][] matrix = CellParse.cellToArray(cell);
			PlayingField pf = new PlayingField(matrix);
//			System.out.println(pf);
			if(!pf.movesAvailable()) {
				return "\"" + -1 + "\"";
			}
			direction = (int)Tree.bruteForce(pf, level, 1, 1,level);
//			logger.info(cell);
			logger.info("direction = " + direction);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to parse the json  " + e);
			String output = "Failed to parse the json  " + e;
			return "\"" + output + "\"";
		}
		return "\"" + direction + "\"";
	}
	
	/*@Path("/cell/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCell(String json) {
		logger.info("cell json = " + json);
		try {
			Cell cell = CellParse.getCell(json);
			logger.info(cell);
		} catch (Exception e) {
			logger.error("Failed to parse the json" + e);
			String output = "Failed to parse the json" + e;
			return Response.status(500).entity(output).build();
		}
		String output = "Got it";
		return Response.status(200).entity(output).build();
	}*/
	
	@Path("/aaa/")
    @GET
    @Produces("application/json") 
    public Object readAccountsByPerson() {
//        return new OS("windows", "xp");
        return new MyJaxbBean("Agamemnon", 32);
    }
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getResult(@QueryParam("name") String name) {
		return result(name);
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String postResult(@QueryParam("name") String name) {
		return result(name);
	}
	
	private String result(String name) {
		return name + ", hello";
	}
	
}
