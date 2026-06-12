package com.coffeeshop.memento;

import com.coffeeshop.command.Command;
import java.util.Stack;

public class OrderCaretaker {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void saveCommand(Command cmd) {
        undoStack.push(cmd);
        redoStack.clear(); // Khi có hành động mới, xóa sạch lịch sử redo
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }
}