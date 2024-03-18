package me.hqqich.aria2.response.field;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : hqqich
 */
@Getter
@Setter
public class Aria2TaskFile {

	String path;
	Long completedLength;
	Long length;
	Integer index;
	Boolean selected;
	List<Uri> uris;

	@Data
	public static class Uri {

		String uri;
		String status;
	}
}