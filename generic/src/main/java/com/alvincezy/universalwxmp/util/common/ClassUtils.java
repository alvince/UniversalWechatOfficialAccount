package com.alvincezy.universalwxmp.util.common;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author alvince.zy@gmail.com
 */
public class ClassUtils {

    private static final int SCOPE_PUBLIC = Modifier.PUBLIC;
    private static final int SCOPE_PROTECTED = Modifier.PROTECTED;
    private static final int SCOPE_PACKAGE = Modifier.ABSTRACT | Modifier.STATIC | Modifier.FINAL | Modifier.TRANSIENT |
            Modifier.VOLATILE | Modifier.SYNCHRONIZED | Modifier.NATIVE | Modifier.STRICT | Modifier.INTERFACE;
    private static final int SCOPE_PRIVATE = Modifier.PRIVATE;

    public enum AccessScope {
        PRIVATE, DEFAULT, PROTECTED, PUBLIC
    }

    public static Field[] getAllMemberFields(Class<?> clazz) {
        Class<?> cls = clazz;
        List<Field> list = new ArrayList<Field>();

        while (obtainClassMemberFields(cls, list, AccessScope.PUBLIC)) {
            cls = cls.getSuperclass();
        }

        return list.toArray(new Field[0]);
    }

    public static Object getFieldValue(Field field, Object owner) throws IllegalAccessException {
        boolean inaccessible = !field.isAccessible();
        if (inaccessible) {
            field.setAccessible(true);
        }

        Object var = field.get(owner);
        if (inaccessible) {
            field.setAccessible(false);
        }

        return var;
    }

    public static boolean isBasicType(Class<?> clazz) {
        return isNumberType(clazz) || clazz.isAssignableFrom(String.class);
    }

    public static boolean isNumberType(Class<?> clazz) {
        return Byte.TYPE.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)
                || Character.TYPE.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)
                || Integer.TYPE.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)
                || Short.TYPE.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz)
                || Long.TYPE.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)
                || Float.TYPE.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)
                || Double.TYPE.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz);
    }

    public static boolean isCollectionType(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    private static boolean obtainClassMemberFields(Class<?> clazz, @NotNull List<Field> fieldsTray, AccessScope scope) {
        if (!StringUtils.equals(Object.class.getName(), clazz.getName())) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if ((mod & Modifier.STATIC) != 0) {
                    continue;
                }

                boolean pick = (mod & SCOPE_PRIVATE) != 0; // check is private default
                switch (scope) {
                    case PUBLIC:
                        pick |= (mod & SCOPE_PUBLIC) != 0;
                    case PROTECTED:
                        pick |= (mod & SCOPE_PROTECTED) != 0;
                    case DEFAULT:
                        pick |= (mod | SCOPE_PACKAGE) == SCOPE_PACKAGE;
                    case PRIVATE:
                    default:
                }

                if (pick) {
                    fieldsTray.add(field);
                }
            }
            return true;
        }
        return false;
    }
}
