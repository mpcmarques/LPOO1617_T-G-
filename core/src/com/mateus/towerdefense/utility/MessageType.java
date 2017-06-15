package com.mateus.towerdefense.utility;

/**
 * Telegram message types.
 */
public final class MessageType {
    private MessageType(){}

    public static final int TARGET_MONSTER = 0;
    public static final int GO_UP = 1;
    public static final int GO_LEFT = 2;
    public static final int GO_RIGHT = 3;
    public static final int SAY_MSG = 4;
    public static final int STOP_MOVING = 5;
    public static final int CONTINUE_MOVING = 6;
    public static final int BUILD_MODE = 7;
    public static final int HIT = 8;            /* EXTRA INFO IS DAMAGE */
    public static final int KILLED_MOB = 9;     /* EXTRA INFO IS GOLD EARNED */
    public static final int PLAYER_DAMAGE = 10; /* EXTRA INFO IS DAMAGE */
}
