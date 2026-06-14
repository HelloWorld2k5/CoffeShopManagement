package com.coffeeshop.command;

import com.coffeeshop.memento.OrderMemento;
import com.coffeeshop.observer.CartSubject;

public class CartUpdateCommand implements Command {
    private CartSubject subject;
    private OrderMemento mementoBefore;
    private OrderMemento mementoAfter;
    private Runnable action;

    public CartUpdateCommand(CartSubject subject, Runnable action) {
        this.subject = subject;
        this.action = action;
    }

    @Override
    public void execute() {
        if (mementoBefore == null) {
            // Lần chạy đầu tiên: Lưu state trước, chạy logic, lưu state sau
            mementoBefore = subject.createMemento();
            action.run();
            mementoAfter = subject.createMemento();
        } else {
            // Lần chạy lại (Redo): Chỉ cần khôi phục state đã chụp sau khi chạy action
            subject.restoreMemento(mementoAfter);
        }
    }

    @Override
    public void undo() {
        // Khôi phục state trước khi chạy action
        subject.restoreMemento(mementoBefore);
    }
}