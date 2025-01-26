package org.kfu.itis.allayarova.orissemesterwork2.service;

public enum Commands {
    INIT_MENU(0),
    ENTER_IN_ROOM(1),
    SEND_ROOM_LIST( 2);

    private final int code;


    Commands(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static int getCodeByName(String name) {
        try {
            return Commands.valueOf(name.toUpperCase()).getCode();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid command name: " + name);
        }
    }

    public static Commands fromCode(int code) {
        for (Commands command : Commands.values()) {
            if (command.code == code) {
                return command;
            }
        }
        throw new IllegalArgumentException("Invalid commands code: " + code);
    }
}
