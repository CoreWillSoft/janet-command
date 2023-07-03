package io.janet.command.test;


import io.janet.Command;
import io.reactivex.rxjava3.functions.Function;

public abstract class BaseContract extends Contract {

    BaseContract(Function<Command, Boolean> predicate) {
        super(predicate);
    }

    public Contract result(Object result) {
        this.result = result;
        return this;
    }

    public Contract exception(Exception exception) {
        this.exception = exception;
        return this;
    }

}
