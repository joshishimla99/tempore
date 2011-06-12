package ar.fi.uba.tempore.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Clase demo para utilizar DTO
 * @author Nicolas Garcia
 *
 */
public class DemoDto implements IsSerializable {
	
	private String string;
	private Integer integer;
	private Double doubl;
	private Boolean bool;
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private List<SubDto> list = new ArrayList<SubDto>();
	
	
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public Map<String, Integer> getMap() {
		return map;
	}
	public void setString(String string) {
		this.string = string;
	}
	public String getString() {
		return string;
	}
	public void setInteger(Integer integer) {
		this.integer = integer;
	}
	public Integer getInteger() {
		return integer;
	}
	public void setDoubl(Double doubl) {
		this.doubl = doubl;
	}
	public Double getDoubl() {
		return doubl;
	}
	public void setBool(Boolean bool) {
		this.bool = bool;
	}
	public Boolean getBool() {
		return bool;
	}
	public List<SubDto> getList() {
		return list;
	}
	public void setList(List<SubDto> list) {
		this.list = list;
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer();
		
		result.append("String: " + string + "\n");
		result.append("Double: " + doubl + "\n");
		result.append("Bool: " + bool + "\n");
		result.append("List: " + list + "\n");
		result.append("Map: " + map + "\n");
		return result.toString();
	}
}
