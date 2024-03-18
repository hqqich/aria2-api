package me.hqqich.aria2.dto.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import lombok.Getter;

/**
 * 请求参数
 *
 * @author : hqqich
 */
@Getter
public class Aria2RequestBody {

	@JsonSerialize(using = ToStringSerializer.class)
	final int id;
	@JsonSerialize(using = ToStringSerializer.class)
	final double jsonrpc = 2.0;
	final String method;
	final List<Object> params;

	public Aria2RequestBody(int id, String method, List<Object> params) {
		this.id = id;
		this.method = method;
		this.params = params;
	}


}
