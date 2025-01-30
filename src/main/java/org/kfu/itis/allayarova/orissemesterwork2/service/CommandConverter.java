package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.models.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandConverter {
    public static String toString(Action<Object> action){
        String valueString = (action.getValue() != null)
                ? (String) action.getValue().stream().map(Object::toString).collect(Collectors.joining(" "))
                : "";
        return action.getCommand().getCode() + ":" + valueString;
    }

    public static List<Action> toMessage(String string){
        List<Action> actions = new ArrayList<>();
        String[] actionArr = string.split(";");

        for(String actionString: actionArr){
            String[] arr = actionString.split(":");
            actions.add(new Action<String>(Commands.fromCode(Integer.parseInt(arr[0])),
                    (ArrayList<String>) Arrays.stream(arr[1].split(" ")).collect(Collectors.toList())));
        }

        return actions;
    }

    public static Action toOneMessage(String string) {
        String[] arr = string.split(":");
        return new Action<String>(Commands.fromCode(Integer.parseInt(arr[0])),
                (ArrayList<String>) Arrays.stream(arr[1].split(" ")).collect(Collectors.toList()));
    }
}
