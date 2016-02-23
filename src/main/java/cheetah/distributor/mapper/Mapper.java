package cheetah.distributor.mapper;

import cheetah.distributor.machine.Machine;
import cheetah.util.ObjectUtils;

import java.util.List;

/**
 * Created by Max on 2016/2/23.
 */
public interface Mapper {

    List<Machine> getMachine(MachineMapperKey mapperKey);

    void put(MachineMapperKey mapperKey, List<Machine> machines);

    boolean isExists(MachineMapperKey mapperKey);

    class MachineMapperKey {
        private final Class<?> eventType;
        private final Class<?> sourceType;

        public MachineMapperKey(Class<?> eventType, Class<?> sourceType) {
            this.eventType = eventType;
            this.sourceType = sourceType;
        }

        public static MachineMapperKey generate(Class<?> eventType, Class<?> sourceType) {
            return new MachineMapperKey(eventType, sourceType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MachineMapperKey that = (MachineMapperKey) o;

            return ObjectUtils.nullSafeEquals(this.eventType, that.eventType) && ObjectUtils.nullSafeEquals(this.sourceType, that.sourceType);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.eventType) * 29 + ObjectUtils.nullSafeHashCode(this.sourceType);
        }
    }
}
