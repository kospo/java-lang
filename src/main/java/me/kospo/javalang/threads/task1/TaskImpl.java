package me.kospo.javalang.threads.task1;

import java.util.concurrent.Callable;

public class TaskImpl<T> implements Task<T> {
    private final Callable<T> callable;

    private volatile boolean mustCalc = true;
    private Exception exception;
    private volatile T result;

    public TaskImpl(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public T get() throws Exception {
        T temp = result;
        if(mustCalc) {
            synchronized (this) {
                if(mustCalc) {
                    try {
                        result = temp = callable.call();
                    } catch (Exception e) {
                        exception = e;
                    }
                    mustCalc = false;
                } else {
                    temp = result;
                }
            }
        }

        if(exception != null) {
            throw exception;
        }

        return temp;
    }
}
