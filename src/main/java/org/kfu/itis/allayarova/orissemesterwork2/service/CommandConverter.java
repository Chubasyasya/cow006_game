package org.kfu.itis.allayarova.orissemesterwork2.service;

import org.kfu.itis.allayarova.orissemesterwork2.models.Action;
import org.kfu.itis.allayarova.orissemesterwork2.models.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandConverter {
//    public static String toString(Action<Object> action){
//        String valueString = (action.getValue() != null)
//                ? (String) action.getValue().stream().map(Object::toString).collect(Collectors.joining(" "))
//                : "";
//        return action.getCommand().getCode() + ":" + valueString;
//    }
//
//    public static List<Action<String>> toMessage(String string){
//        List<Action<String>> actions = new ArrayList<>();
//        String[] actionArr = string.split(";");
//
//        for(String actionString: actionArr){
//            String[] arr = actionString.split(":");
//            actions.add(new Action<String>(Commands.fromCode(Integer.parseInt(arr[0])),
//                    (ArrayList<String>) Arrays.stream(arr[1].split(" ")).collect(Collectors.toList())));
//        }
//
//        return actions;
//    }
//
//    public static Action toOneMessage(String string) {
//        String[] arr = string.split(":");
//        return new Action<String>(Commands.fromCode(Integer.parseInt(arr[0])),
//                (ArrayList<String>) Arrays.stream(arr[1].split(" ")).collect(Collectors.toList()));
//    }

    public static Message actionToMessage(Action action) {
        String valueString = (action.getValue() != null)
                ? (String) action.getValue().stream().map(Object::toString).collect(Collectors.joining(" "))
                : "";
        return new Message(action.getCommand().getCode(), valueString);
    }

    public static Action<String> messageToAction(Message message) {
        List<String> data = Arrays.stream(message.getData().split(" ")).toList();
        Action action = new Action<>(Commands.getNameByCode(message.getType()), data);
        return action;
    }

    public static String messageToString(Message message){
        return message.getType() + ":" + message.getData();
    }

    public static Message stringToMessage(String string) {
        String[] arr = string.split(":");
        return new Message(Integer.parseInt(arr[0]), arr[1]);
    }
}
