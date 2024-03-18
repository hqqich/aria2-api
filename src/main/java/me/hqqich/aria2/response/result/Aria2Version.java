package me.hqqich.aria2.response.result;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import me.hqqich.aria2.response.clazz.Aria2Response;

/**
 * @author : hqqich
 */
@Getter
@Setter
public class Aria2Version {

	List<String> enabledFeatures;
	String version;

	public static class Response extends Aria2Response<Aria2Version> {

	}
}
