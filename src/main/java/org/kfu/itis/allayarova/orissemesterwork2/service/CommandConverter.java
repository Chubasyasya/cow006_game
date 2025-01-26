package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.models.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandConverter {
    public static String toString(Action action){
        String valueString = (action.getValue() != null)
                ? (String) action.getValue().stream().map(Object::toString).collect(Collectors.joining(" "))
                : "";
        return action.getCommand().getCode() + ":" + valueString;
    }

    public static Action toMessage(String string){
        String[] arr = string.split(":");
        return new Action<String>(Commands.fromCode(Integer.parseInt(arr[0])),
                (ArrayList<String>) Arrays.stream(arr[1].split(" ")).collect(Collectors.toList()));
    }
}
