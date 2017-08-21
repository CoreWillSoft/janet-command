package io.janet.command.sample;

import io.janet.CommandActionService;
import io.janet.Janet;
import io.janet.command.sample.actions.OtherCommandWithResult;
import io.janet.command.sample.actions.CommandWithResult;
import io.janet.command.sample.actions.CommandWithType;
import io.janet.command.test.BaseContract;
import io.janet.command.test.MockCommandActionService;

public class MockCommandSample {

    public static void main(String... args) throws Throwable {
        Janet janet = new Janet.Builder()
                .addService(new MockCommandActionService.Builder()
                        .actionService(new CommandActionService())
                        .addContract(BaseContract.of(CommandWithType.class)
                                .filter(commandWithType -> commandWithType.getType() == CommandWithType.Type.BAR)
                                .result("Result 3"))
                        .addContract(BaseContract.of(CommandWithResult.class).result("Result 2"))
                        .addContract(BaseContract.of(OtherCommandWithResult.class).exception(new IllegalStateException()))
                        .build()
                )
                .build();

        janet.createPipe(CommandWithResult.class)
                .createObservableResult(new CommandWithResult())
                .subscribe(commandWithResult -> System.out.println(commandWithResult.getResult()));

        janet.createPipe(CommandWithType.class)
                .createObservableResult(new CommandWithType(CommandWithType.Type.BAR))
                .subscribe(commandWithResult -> System.out.println(commandWithResult.getResult()));

        janet.createPipe(CommandWithType.class)
                .createObservableResult(new CommandWithType(CommandWithType.Type.FOO))
                .subscribe(commandWithResult -> System.out.println(commandWithResult.getResult()));

        janet.createPipe(OtherCommandWithResult.class)
                .createObservableResult(new OtherCommandWithResult())
                .subscribe(commandWithResult -> System.out.println(commandWithResult.getResult()),
                        e -> System.out.println(e.getCause().toString()));
    }
}
