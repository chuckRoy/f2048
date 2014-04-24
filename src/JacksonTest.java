

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.zxk.entity.Cell;
import com.zxk.entity.Grid;

public class JacksonTest {
	
	//"[[null,null,{"x":0,"y":2,"value":2,"previousPosition":null,"mergedFrom":null},null],[null,null,null,null],[null,null,null,null],[null,null,null,{"x":3,"y":3,"value":2,"previousPosition":null,"mergedFrom":null}]]"
	public static void main(String[] args) throws JsonProcessingException, IOException {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//		System.out.println(objectMapper.writeValueAsString("File uploaded to : D://aa.jpg"));
		
		//read
//		Agent agnet = objectMapper.readValue(string, Agent.class);
//		System.out.println(agnet.toString());
		//List<List<Grid>> cell;
		Cell cell = new Cell();
		List<List<Grid>> gridLists = new ArrayList<List<Grid>>();
		
//		String json = "[[null,null,{\"x\":0,\"y\":2,\"value\":2,\"previousPosition\":null,\"mergedFrom\":null},null],[null,null,null,null],[null,null,null,null],[null,null,null,{\"x\":3,\"y\":3,\"value\":2,\"previousPosition\":null,\"mergedFrom\":null}]]";
		String json = "[[{\"x\":0,\"y\":0,\"value\":8,\"previousPosition\":{\"x\":0,\"y\":0},\"mergedFrom\":null},{\"x\":0,\"y\":1,\"value\":2,\"previousPosition\":{\"x\":1,\"y\":1},\"mergedFrom\":null},null,{\"x\":0,\"y\":3,\"value\":2,\"previousPosition\":{\"x\":0,\"y\":3},\"mergedFrom\":null}],[null,null,null,{\"x\":1,\"y\":3,\"value\":2,\"previousPosition\":null,\"mergedFrom\":null}],[null,null,null,null],[null,null,null,null]]";
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<List<LinkedHashMap<String, Object>>> list = objectMapper.readValue(json, List.class);
		for (int i = 0; i < 4; i++) {
			List<Grid> grids = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				Grid g = new Grid();
				Map<String, Object> map = list.get(i).get(j);
				if(map == null) {grids.add(g);continue;}
				Set<String> set = map.keySet();
				for (Iterator<String> it = set.iterator();it.hasNext();) {
					String key = it.next();
					switch (key) {
					case "x":
						g.setX((int) map.get(key));
						break;
					case "y":
						g.setY((int) map.get(key));
						break;
					case "value":
						g.setValue((int) map.get(key));
						break;
//					case "previousPosition":
//						g.setPreviousPosition((String) map.get(key));
//						break;
//					case "mergedFrom":
//						g.setMergedFrom((String) map.get(key));
//						break;	
					default:
						break;
					}
				}
				grids.add(g);
			}
			gridLists.add(grids);
		}
		
		cell.setCellLists(gridLists);
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Grid g = gridLists.get(i).get(j);
				System.out.println(i + " " + j + "; " + g + ";  " + (g==null));
			}
		}
		
		
//		String g2 = (String)list.get(0).get(2);
//		System.out.println(g2);
//		Grid g = objectMapper.readValue(list.get(0).get(2), Grid.class);
//		System.out.println(g);
	}
	
	
}
