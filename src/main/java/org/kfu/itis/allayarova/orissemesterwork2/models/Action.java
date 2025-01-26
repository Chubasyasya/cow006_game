package org.kfu.itis.allayarova.orissemesterwork2.models;

import org.kfu.itis.allayarova.orissemesterwork2.service.Commands;

import java.util.ArrayList;
import java.util.List;

public class Action<E>{
    private Commands command;
    private List<E> value;

    public Action(Commands command, ArrayList<E> value) {
        this.command=command;
        this.value=value;

    }

    public Commands getCommand() {
        return command;
    }

    public void setCommand(Commands command) {
        this.command = command;
    }

    public List<E> getValue() {
        return value;
    }

    public void setValue(List<E> value) {
        this.value = value;
    }
}
