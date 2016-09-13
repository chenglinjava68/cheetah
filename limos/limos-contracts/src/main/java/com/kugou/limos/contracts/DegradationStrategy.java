package com.kugou.limos.contracts;

/**
 * 降级策略
 * Created by Max on 2016/9/3.
 */
public interface DegradationStrategy {

    boolean isAvailable();
    
}
