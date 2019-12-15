package cn.mokier.outdoorspresent.customentity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_14_R1.*;

import java.lang.reflect.Method;
import java.util.Map;

public class EntityLoader {

    @SuppressWarnings("rawtypes")
    public static EntityTypes PRESENT;

    /**
     * 加载自定义生物
     */
    public static void load() {
        PRESENT = injectNewEntity("custom_present", "present", PresentLiving::new, EnumCreatureType.MISC);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static EntityTypes injectNewEntity(String name, String extend_from, EntityTypes.b<? extends EntityShulker> entitytypes, EnumCreatureType type) {
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a()
                .getSchema(DataFixUtils.makeKey(SharedConstants.a().getWorldVersion()))
                .findChoiceType(DataConverterTypes.o)
                .types();
        dataTypes.put("minecraft:" + name, dataTypes.get("minecraft:" + extend_from));

        try {
            Method a = EntityTypes.class.getDeclaredMethod("a", String.class, EntityTypes.a.class);
            a.setAccessible(true);
            Object ret = a.invoke(null, name, EntityTypes.a.a(entitytypes, type));
            return (EntityTypes) ret;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
