package cheetah.dispatcher.mapper.support;

import cheetah.dispatcher.machine.Machine;
import cheetah.dispatcher.mapper.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/2/23.
 */
public class MachineMapper implements Mapper {
    private final Map<MachineMapperKey, List<Machine>> machineMap = new ConcurrentHashMap<>();

    @Override
    public List<Machine> getMachine(MachineMapperKey mapperKey) {
        return isExists(mapperKey) ? machineMap.get(mapperKey) : Collections.EMPTY_LIST;
    }

    @Override
    public void put(MachineMapperKey mapperKey, List<Machine> machines) {
        machineMap.put(mapperKey, machines);
    }

    @Override
    public boolean isExists(MachineMapperKey mapperKey) {
        return machineMap.containsKey(mapperKey);
    }
}
