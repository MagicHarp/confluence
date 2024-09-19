package org.confluence.mod.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * 所有的定时延迟执行/单次延迟执行方法都用这个
 */
public class DelayedTaskExecutor {
    public static class DelayedTask {
        // 任务信息变量
        private final int delay;
        private final boolean reoccuring;
        protected Runnable callBack;
        // 实际加入使用时赋值
        protected int runAtTick = -1;

        /**
         * 新建一个单次的延迟执行任务（注：延迟以任务塞进DelayedTaskExecutor时为基准进行计时）
         *
         * @param callBack 延迟执行的任务内容
         * @param delay    多久后延迟执行
         */
        public DelayedTask(Runnable callBack, int delay) {
            this(callBack, delay, false);
        }

        /**
         * 新建一个延迟执行任务（注：延迟以任务塞进DelayedTaskExecutor时为基准进行计时）
         *
         * @param callBack    延迟执行的任务内容
         * @param delay       多久后延迟执行
         * @param reoccurring true：以delay为间隔重复执行，在注册后的下一刻进行第一次执行
         *                    false：在delay刻之后执行一次
         */
        public DelayedTask(Runnable callBack, int delay, boolean reoccurring) {
            this.callBack = callBack;
            this.delay = delay;
            this.reoccuring = reoccurring;
        }
    }

    // 使用类似于单例的方法
    public static final Hashtable<String, DelayedTaskExecutor> executors = new Hashtable<>();

    /**
     * 若服务器对应的实例暂不存在，将会创建一个并基于server每刻触发的机制运作
     *
     * @param server 需要新建实例时依赖的服务器
     */
    public static DelayedTaskExecutor getInstance(String profiler, MinecraftServer server) {
        return executors.computeIfAbsent(profiler, p -> new DelayedTaskExecutor(server));
    }

    // 延迟单次变量，使用TreeMap获得综合最佳的添加/访问速度
    private final Int2ObjectAVLTreeMap<Collection<DelayedTask>> singularTasks;
    // 所有的定时运行任务
    private final ArrayList<DelayedTask> timerTasks;
    private int currentTick;

    // constructor，初始化数据结构并记录为第一刻
    private DelayedTaskExecutor(MinecraftServer server) {
        this.singularTasks = new Int2ObjectAVLTreeMap<>();
        this.timerTasks = new ArrayList<>();
        this.currentTick = 0;

        server.addTickable(this::tick);
    }

    /**
     * 准备运行一个DelayedTask
     */
    public void registerTask(DelayedTask task) {
        // 以固定间隔执行：下一刻进行第一次运行
        if (task.reoccuring) {
            task.runAtTick = currentTick + 1;
            timerTasks.add(task);
        }
        // 单次执行
        else {
            task.runAtTick = currentTick + task.delay;
            // 若移除操作频繁可酌情将ArrayList换成HashSet
            singularTasks
                    .computeIfAbsent(task.runAtTick, (t) -> new ArrayList<>())
                    .add(task);
        }
    }

    /**
     * 试图移除一个DelayedTask，若任务已结束或不存在也不会出现问题
     */
    public void removeTask(DelayedTask task) {
        // 以固定间隔执行的任务
        if (task.reoccuring) {
            timerTasks.remove(task);
        }
        // 单次执行任务
        else {
            if (singularTasks.containsKey(task.runAtTick)) {
                singularTasks.get(task.runAtTick).remove(task);
            }
        }
    }

    // tick方法
    private void tick() {
        // 固定间隔任务
        if (singularTasks.containsKey(currentTick)) {
            for (DelayedTask task : singularTasks.get(currentTick)) {
                task.callBack.run();
            }
            singularTasks.remove(currentTick);
        }

        // 重复执行的任务
        for (DelayedTask task : timerTasks) {
            if ((currentTick - task.runAtTick) % task.delay == 0) {
                task.callBack.run();
            }
        }

        currentTick++;
    }
}
