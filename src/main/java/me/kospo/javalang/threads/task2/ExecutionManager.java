package me.kospo.javalang.threads.task2;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
