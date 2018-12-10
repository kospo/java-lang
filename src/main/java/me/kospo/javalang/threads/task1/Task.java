package me.kospo.javalang.threads.task1;

public interface Task<T> {
    //calc 1 time, multiThread
    T get() throws Exception;
}
