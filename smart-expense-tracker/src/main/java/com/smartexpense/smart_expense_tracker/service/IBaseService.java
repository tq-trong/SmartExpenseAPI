package com.smartexpense.smart_expense_tracker.service;

public interface IBaseService<T> {
    T create(T dto);

    T get(String id);
}
