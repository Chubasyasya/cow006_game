package org.kfu.itis.allayarova.orissemesterwork2.service;

public enum Commands {
    INIT_MENU(0),
    ENTER_IN_ROOM(1),
    SEND_ROOM_LIST( 2),
    START_GAME(3),
    GET_CARDS(4),
    FIELD_INIT(5),
    SELECT_CARD(6),
    DO_NOTHING(7),
    NOTIFY_NEXT(8),
    SELECT_ROW_TO_PICK(9),
    PUT_CARD_ON_TABLE(10),
    UPDATE_PLAYING_FIELD(11),
    ROUND_COMPLETED(12),
    GAME_RESULT(13); // Название команды : занчение(0 - поражение, 1 - победа, 2 - ни выиграл и не проиграл)

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
