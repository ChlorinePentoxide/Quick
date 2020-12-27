package org.chlorinepentoxide.quick.parallelprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelProcessor {

    private ExecutorService executorService;

    private List<Callable<Void>> stack;

    public ParallelProcessor(int threads) {
        executorService = Executors.newFixedThreadPool(threads);
        stack = new ArrayList<>();
    }

    public void newEvent(Callable<Void> event) {
        stack.add(event);
    }

    public void flushStack() {
        stack.clear();
    }

    public void executeAll() throws InterruptedException {
        executorService.invokeAll(stack);
    }

}
