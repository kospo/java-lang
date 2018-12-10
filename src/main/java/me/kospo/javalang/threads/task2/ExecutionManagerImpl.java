package me.kospo.javalang.threads.task2;

import java.util.ArrayList;
import java.util.List;

public class ExecutionManagerImpl implements ExecutionManager {
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        return new ContextImpl(callback, tasks);
    }
}
