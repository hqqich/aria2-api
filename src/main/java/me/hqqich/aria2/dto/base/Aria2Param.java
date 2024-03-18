package me.hqqich.aria2.dto.base;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import me.hqqich.aria2.enums.Aria2Method;
import org.jetbrains.annotations.NotNull;

/**
 * 请求参数
 *
 * @author : hqqich
 */
@Getter
public class Aria2Param {

	@NotNull
	final Aria2Method methodName;
	@NotNull
	final List<Object> params;

	public Aria2Param(@NotNull Aria2Method methodName, @NotNull List<Object> params) {
		this.methodName = methodName;
		this.params = params;
	}

	public Aria2Param(@NotNull Aria2Method methodName, Object... params) {
		this.methodName = methodName;
		this.params = Arrays.asList(params);
	}

	public Aria2Param(@NotNull Aria2Method methodName, String gid) {
		this.methodName = methodName;
		this.params = Collections.singletonList(gid);
	}

	/**
	 * 分页查询参数的构造方法
	 */
	public Aria2Param(@NotNull Aria2Method methodName, int page, int size, String... keys) {
		this.methodName = methodName;
		this.params = Arrays.asList(Math.max(0, (page - 1) * size), size, Arrays.asList(keys));
	}

	public static List<Aria2Param> listOf(@NotNull Aria2Method methodName, Collection<String> gid) {
		return gid.stream().map(g -> new Aria2Param(methodName, g)).collect(Collectors.toList());
	}

}
