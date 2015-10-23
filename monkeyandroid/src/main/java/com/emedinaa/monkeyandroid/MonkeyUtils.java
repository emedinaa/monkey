package com.emedinaa.monkeyandroid;

/**
 * Created by emedinaa on 23/10/15.
 */
public class MonkeyUtils {

    static <T> void validateInterface(Class<T> clientInterface)
    {
        if (!clientInterface.isInterface()) {
            throw new IllegalArgumentException("Monkey, debe ser una interfaz");
        }

        if (clientInterface.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Monkey, no debe implementar de otra interfaz");
        }
    }
}
