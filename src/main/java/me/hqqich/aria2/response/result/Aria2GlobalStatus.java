package me.hqqich.aria2.response.result;

import lombok.Getter;
import lombok.Setter;
import me.hqqich.aria2.response.clazz.Aria2Response;

/**
 * 概况统计
 *
 * @author : hqqich
 */
@Getter
@Setter
public class Aria2GlobalStatus {

    Integer numActive;
    Integer numWaiting;
    Long downloadSpeed;
    Long uploadSpeed;
    Integer numStopped;
    Integer numStoppedTotal;

    public static class Response extends Aria2Response<Aria2GlobalStatus> {

    }
}
