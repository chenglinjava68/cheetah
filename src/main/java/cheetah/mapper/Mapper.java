package cheetah.mapper;

import cheetah.event.Event;
import cheetah.machine.Machine;
import cheetah.util.ObjectUtils;

import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/**
 * Created by Max on 2016/2/23.
 */
public interface Mapper extends Cloneable {

    Map<Class<? extends EventListener>, Machine> getMachine(MachineMapperKey mapperKey);

    void put(MachineMapperKey mapperKey, Map<Class<? extends EventListener>, Machine> machines);

    Set<MachineMapperKey> mapperKeys();

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

        public static MachineMapperKey generate(Event event) {
            return new MachineMapperKey(event.getClass(), event.getSource().getClass());
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
