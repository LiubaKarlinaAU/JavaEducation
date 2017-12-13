package ru.spbau.karlina.test2;

import ru.spbau.karlina.test2.exceptions.AmbiguousImplementationException;
import ru.spbau.karlina.test2.exceptions.ImplementationNotFoundException;
import ru.spbau.karlina.test2.exceptions.InjectionCycleException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class Injector {

    static Object initialize(String rootClassName, Collection<String> collection)
            throws AmbiguousImplementationException,
            ImplementationNotFoundException, InjectionCycleException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        Class<Object> clazz = null;

        try {
            clazz = (Class<Object>) Class.forName(rootClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Constructor constructors[] = clazz.getConstructors();
        if (constructors.length > 1) {
            throw new AmbiguousImplementationException("Too much constructors for " + rootClassName + " class.");
        }

        Constructor constructor = constructors[0];
        Class<?> parametrTypes[] = constructor.getParameterTypes();
        if (parametrTypes.length == 0) {
            return constructor.newInstance();
        }

        ArrayList<Object> parametrs = new ArrayList<Object>();
        for (int i = 0; i < parametrTypes.length; ++i) {
            Object current = parametrTypes[i];

            for (Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
                String found = iterator.next();
                if (current.getClass().getCanonicalName() == found) {
                    parametrs.add(initialize(found, collection));
                }


                if (parametrs.size() != i + 1) {
                    throw new ImplementationNotFoundException("Can't find " + current.getClass().getCanonicalName() + " realisation.");
                }
            }
        }

        return constructor.newInstance(parametrs);
    }
}
