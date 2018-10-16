package me.kospo.javalang.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {
    public static void main(String[] args) {
        Test test1 = new Test();
        test1.t1 = 1;
        test1.t2 = new ArrayList();

        Test test2 = new Test();

        System.out.println("test1 = " + test1);
        System.out.println("test2 = " + test2);

        assign(test2, test1);

        System.out.println("test2 = " + test2);
    }

    /**
     *      * Scans object "from" for all getters. If object "to"
     *      * contains correspondent setter, it will invoke it
     *      * to set property value for "to" which equals to the property
     *      * of "from".
     *      * <p/>
     *      * The type in setter should be compatible to the value returned
     *      * by getter (if not, no invocation performed).
     *      * Compatible means that parameter type in setter should
     *      * be the same or be superclass of the return type of the getter.
     *      * <p/>
     *      * The method takes care only about public methods.
     *      *
     *      * @param to   Object which properties will be set.
     *      * @param from Object which properties will be used to get values.
     *      
     */
    public static void assign(Object to, Object from) {
        try {
            Map<String, Method> getters = getGetters(from);
            Map<String, Method> setters = getSetters(to);

            for (Map.Entry<String, Method> getterEntry : getters.entrySet()) {
                Method setter = getSetter(setters, getterEntry);
                if (setter != null) {
                    Method getter = getterEntry.getValue();

                    getter.setAccessible(true);
                    Object field = getter.invoke(from);

                    setter.setAccessible(true);
                    setter.invoke(to, field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method getSetter(Map<String, Method> setters, Map.Entry<String, Method> getterEntry) {
        String getterFieldName = getterEntry.getKey();
        Method getterMethod = getterEntry.getValue();

        for (Map.Entry<String, Method> setterEntry : setters.entrySet()) {
            String setterFieldName = setterEntry.getKey();
            Method setterMethod = setterEntry.getValue();

            if(
                    getterFieldName.equals(setterFieldName) &&
                    setterMethod.getParameterTypes()[0].isAssignableFrom(getterMethod.getReturnType())
            ) {
                return setterMethod;
            }
        }

        return null;
    }

    private static <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static <T> Map<String, Method> getGetters(T from) {
        Map<String, Method> ret = new HashMap<>();

        List<Field> fields = getFields(from);

        for (Field field : fields) {
            List<String> chkNames = new ArrayList<>(2);
            Class<?> fieldType = field.getType();

            String cName = capitalize(field.getName());

            if (fieldType.equals(Boolean.class)) {
                chkNames.add("is" + cName);
            }
            chkNames.add("get" + cName);

            for (Method method : getMethods(from)) {
                if (chkNames.contains(method.getName()) && isGetter(method, field)) {
                    ret.put(field.getName(), method);

                    break;
                }
            }
        }

        return ret;
    }

    private static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static <T> Map<String, Method> getSetters(T from) {
        Map<String, Method> ret = new HashMap<>();

        List<Field> fields = getFields(from);

        for (Field field : fields) {
            String mName = "set" + capitalize(field.getName());

            for (Method method : getMethods(from)) {
                if (method.getName().equals(mName) && isSetter(method, field)) {
                    ret.put(field.getName(), method);

                    break;
                }
            }
        }

        return ret;
    }

    private static boolean isSetter(Method method, Field field) {
        return method.getParameterCount() == 1 &&
                method.getParameterTypes()[0].isAssignableFrom(field.getType());
    }

    private static boolean isGetter(Method method, Field field) {
        return method.getParameterCount() == 0 &&
                method.getReturnType().isAssignableFrom(field.getType());
    }

    private static <T> List<Method> getMethods(T t) {
        List<Method> methods = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            clazz = clazz.getSuperclass();
        }
        return methods;
    }
}

