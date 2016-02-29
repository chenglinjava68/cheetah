package cheetah.mapper.support;

import cheetah.machine.Machine;
import cheetah.mapper.Mapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
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
