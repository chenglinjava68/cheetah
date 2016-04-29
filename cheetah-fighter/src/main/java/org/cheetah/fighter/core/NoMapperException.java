package org.cheetah.fighter.core;

/**
 * Created by Max on 2016/2/23.
 */
public class NoMapperException extends FighterException {
    public NoMapperException() {
        super("Couldn't find the corresponding mapping.");
    }
}
