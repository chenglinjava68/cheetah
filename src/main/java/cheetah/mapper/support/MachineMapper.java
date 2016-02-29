package cheetah.mapper.support;

import cheetah.machine.Machine;
import cheetah.mapper.Mapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 机器映射器
 * 在调度中心接受到一个事件后，调度会将其封装为一个Machine， 将其存到本映射器中。
 * 调度中每次都会检查映射器中有没有对应的工作机器，没有则创建，有则直接获取，不会每次都会创建
 * Created by Max on 2016/2/23.
 */
public class MachineMapper implements Mapper {
    private final Map<MachineMapperKey, Map<Class<? extends EventListener>, Machine>> machineMap = new ConcurrentHashMap<>();

    @Override
    public Map<Class<? extends EventListener>, Machine> getMachine(MachineMapperKey mapperKey) {
        return isExists(mapperKey) ? Collections.unmodifiableMap(machineMap.get(mapperKey)) : Collections.EMPTY_MAP;
    }

    @Override
    public void put(MachineMapperKey mapperKey, Map<Class<? extends EventListener>, Machine> machines) {
        if (machines.isEmpty()) {
            return;
        }
        machineMap.put(mapperKey, machines);
    }

    @Override
    public Set<MachineMapperKey> mapperKeys() {
        return machineMap.keySet();
    }

    @Override
    public boolean isExists(MachineMapperKey mapperKey) {
        return machineMap.containsKey(mapperKey);
    }
}
