package com.eastedu.graphics.core.listener;

import com.eastedu.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用来处理任务中断
 *
 * @author ZhenZhong
 */
@Slf4j
public class HandlerSemaphore {
    private static final Map<Long, Boolean> STATS = new ConcurrentHashMap<>();
    private static final Map<Long, List<Long>> THREAD_INFO = new ConcurrentHashMap<>();

    private HandlerSemaphore() {
    }

    /**
     * 启动任务时，会记录每个线程的信息
     * <p>
     * 如果发生错误，则状态设置为false，这时不再记录信息，防止后面的线程加入后报错
     *
     * @param taskId the task id
     * @return the boolean
     */
    public static boolean record(Long taskId) {
        boolean running = isRunning(taskId);
        if (running) {
            THREAD_INFO.computeIfAbsent(taskId, id -> new CopyOnWriteArrayList<>()).add(Thread.currentThread().getId());
        }
        return running;
    }

    /**
     * 启动时，设置running状态
     *
     * @param taskId  the task id
     * @param running the running
     */
    public static void set(Long taskId, boolean running) {
        STATS.put(taskId, running);
    }

    /**
     * Is running boolean.
     *
     * @param taskId the task id
     * @return the boolean
     */
    public static boolean isRunning(Long taskId) {
        Boolean s = STATS.get(taskId);
        return s != null && s;
    }

    /**
     * Failure boolean.
     *
     * @param taskId  the task id
     * @param message the message
     * @return the boolean
     */
    public static synchronized boolean failure(Long taskId, String message) {
        log.error("{} -> 任务异常 -> {}", taskId, message);
        return remove(taskId);
    }

    /**
     * Success boolean.
     *
     * @param taskId the task id
     * @return the boolean
     */
    public static synchronized boolean success(Long taskId) {
        log.info("{} -> 任务结束", taskId);
        return remove(taskId);
    }

    private static boolean remove(Long taskId) {
        List<Long> threadIds = THREAD_INFO.remove(taskId);
        return !CollectionUtils.isEmpty(threadIds) && STATS.remove(taskId);
    }
}
