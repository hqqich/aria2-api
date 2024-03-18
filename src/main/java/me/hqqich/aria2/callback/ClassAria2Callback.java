package me.hqqich.aria2.callback;

import lombok.Setter;
import me.hqqich.aria2.response.clazz.Aria2Response;
import me.hqqich.aria2.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 指定返回类型的回调
 *
 * @author : hqqich
 */
@Setter
public abstract class ClassAria2Callback<T> extends AbstractAria2Callback {

	Class<? extends Aria2Response<T>> eClass;

	@Override
	public final void onSuccess(@NotNull String body) {
		final Aria2Response<T> response = JsonUtils.parse(body, eClass);
		onSuccess(response == null ? null : response.getResult());
	}

	/**
	 * 执行成功回调
	 *
	 * @param res 响应对象
	 */
	abstract public void onSuccess(@Nullable T res);
}
