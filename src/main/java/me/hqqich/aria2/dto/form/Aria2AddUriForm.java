package me.hqqich.aria2.dto.form;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hqqich.aria2.dto.base.Aria2Option;
import me.hqqich.aria2.dto.base.Aria2Param;
import me.hqqich.aria2.enums.Aria2Method;
import me.hqqich.aria2.response.clazz.Aria2Response;

/**
 * 添加下载任务参数
 *
 * @author : hqqich
 */
@RequiredArgsConstructor
@Getter
public class Aria2AddUriForm {

	final Collection<String> urls;
	final Aria2Option params;

	public Aria2Param buildParam() {
		return new Aria2Param(Aria2Method.addUri, urls, params);
	}

	public static class Response extends Aria2Response<List<List<String>>> {

	}
}   
