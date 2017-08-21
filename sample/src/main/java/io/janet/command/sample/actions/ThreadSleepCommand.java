package io.janet.command.sample.actions;

import io.janet.Command;
import io.janet.command.annotations.CommandAction;

@CommandAction
public class ThreadSleepCommand extends Command<String> {

    public final static long DURATION = 10 * 1000;

    @Override protected void run(CommandCallback<String> callback) {
        new Thread(() -> {
            int seconds = 0;
            while (!isCanceled()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    callback.onFail(e);
                }
                callback.onProgress((int) ((++seconds * 100) / (DURATION / 1000)));
            }
        callback.onSuccess("FINISHED");
        }).start();
    }

}
