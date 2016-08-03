package org.cheetah.fighter.worker;

/**
 * Created by maxhuang on 2016/8/3.
 */
public interface WorkerAdapterFactory<T> {

    WorkerAdapter createWorkerAdapter(T workers);
}
