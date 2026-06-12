package com.coffeeshop.command;

public interface Command {
    void execute();
    void undo();
}