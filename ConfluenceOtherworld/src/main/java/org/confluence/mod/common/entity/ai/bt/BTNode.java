package org.confluence.mod.common.entity.ai.bt;

public abstract class BTNode {
    public void start() {}
    public void stop() {}

    /// 具有启动条件的节点可先确认条件，再停止旧动作，最后接管导航。
    public BTStatus tryPreempt(Runnable stopCurrent) {
        start();
        BTStatus status = execute();
        if (status != BTStatus.FAILURE) stopCurrent.run();
        return status;
    }
    public abstract BTStatus execute();
}
