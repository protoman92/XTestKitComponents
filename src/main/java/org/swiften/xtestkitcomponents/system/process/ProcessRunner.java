package org.swiften.xtestkitcomponents.system.process;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.exec.*;
import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.util.HPLog;
import org.swiften.javautilities.object.HPObjects;

import java.io.*;
import java.util.function.Consumer;

/**
 * Created by haipham on 3/22/17.
 */
public class ProcessRunner {
    /**
     * Log the command being executed.
     * @param args {@link String} command to be executed.
     */
    private void logCommand(@NotNull String args) {
        HPLog.printft("Executing '%s'", args);
    }

    /**
     * Execute a command that quickly returns an output using the command line.
     * This operation blocks.
     * @param args The command line arguments to run.
     * @param onNext {@link Consumer} instance to process output.
     * @param onError {@link Consumer} instance to process {@link IOException}.
     * @param onComplete {@link Runnable} instance to process completion.
     * @see Consumer#accept(Object)
     * @see HPObjects#nonNull(Object)
     * @see Runnable#run()
     * @see #logCommand(String)
     */
    public void execute(@NotNull String args,
                        @NotNull Consumer<String> onNext,
                        @NotNull Consumer<IOException> onError,
                        @NotNull Runnable onComplete) {
        logCommand(args);
        CommandLine commandLine = CommandLine.parse(args);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DefaultExecutor executor = new DefaultExecutor();

        /* Capture the output in a separate outputStream. Error messages will
         * also be redirected there */
        PumpStreamHandler handler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(handler);

        try {
            executor.execute(commandLine);
            String output = outputStream.toString();
            output = HPObjects.nonNull(output) ? output : "";
            onNext.accept(output);
            onComplete.run();
        } catch (IOException e) {
            String err = e.getMessage();
            String error = outputStream.toString();
            HPLog.printft("Error running '%s': '%s' - %s", args, err, error);
            onError.accept(e);
        } finally {
            try {
                outputStream.close();
                handler.stop();
            } catch (IOException e) {
                onError.accept(e);
            }
        }
    }

    /**
     * Same as above, but ignore complete event.
     * @param args {@link String} command to execute.
     * @param onNext {@link Consumer} instance to consume output.
     * @param onError {@link Consumer} instance to consume error.
     * @see #execute(String, Consumer, Consumer, Runnable)
     */
    public void execute(@NotNull String args,
                        @NotNull Consumer<String> onNext,
                        @NotNull Consumer<IOException> onError) {
        execute(args, onNext, onError, () -> {});
    }

    /**
     * Same as above, but ignore error/completion events.
     * @param args {@link String} command to execute.
     * @param onNext {@link Consumer} instance to consume output.
     * @see #execute(String, Consumer, Consumer)
     */
    public void execute(@NotNull String args, @NotNull Consumer<String> onNext) {
        execute(args, onNext, HPLog::println);
    }

    /**
     * Same as above, but ignore all events.
     * @param args {@link String} command to execute.
     * @see #execute(String, Consumer)
     */
    public void execute(@NotNull String args) {
        execute(args, HPLog::println);
    }

    /**
     * Execute a command using the command line and wrap the result in a
     * {@link Flowable}.
     * @param ARGS The command line arguments to run.
     * @return {@link Flowable} instance.
     * @see #execute(String, Consumer, Consumer, Runnable)
     */
    @NotNull
    @SuppressWarnings("CodeBlock2Expr")
    public Flowable<String> rxa_execute(@NotNull final String ARGS) {
        final ProcessRunner THIS = this;

        return Flowable.<String>create(a -> {
                THIS.execute(ARGS, a::onNext, a::onError, a::onComplete);
            }, BackpressureStrategy.BUFFER)
            .serialize()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .doOnError(e -> HPLog.printft("Error %s with %s", e, ARGS));
    }

    /**
     * Execute a command with constant output. This is useful for long-running
     * processes. This operation blocks.
     * @param args {@link String} command to be executed.
     * @param onNext {@link Consumer} instance to consume output.
     * @param onError {@link Consumer} instance to consume {@link IOException}.
     * @param onComplete {@link Runnable} instance to handle completion.
     * @see Consumer#accept(Object)
     * @see HPObjects#nonNull(Object)
     * @see Runnable#run()
     * @see #logCommand(String)
     */
    public void executeStream(@NotNull String args,
                              @NotNull Consumer<String> onNext,
                              @NotNull Consumer<IOException> onError,
                              @NotNull Runnable onComplete) {
        logCommand(args);
        CommandLine command = new CommandLine(args);
        DefaultExecutor executor = new DefaultExecutor();
        PipedOutputStream output = new PipedOutputStream();
        PumpStreamHandler pump = new PumpStreamHandler(output);
        ExecuteResultHandler handler = new DefaultExecuteResultHandler();

        PipedInputStream inputStream = null;
        InputStreamReader streamReader = null;
        BufferedReader bufferedReader = null;

        executor.setStreamHandler(pump);
        executor.setExitValue(0);

        try {
            inputStream = new PipedInputStream(output);
            streamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(streamReader);
            executor.execute(command, handler);

            String line;

            while (HPObjects.nonNull(line = bufferedReader.readLine())) {
                onNext.accept(line);
            }

            onComplete.run();
        } catch (IOException e) {
            String err = e.getMessage();
            HPLog.printf("Error running '%s': '%s'", args, err);
            onError.accept(e);
        } finally {
            try {
                pump.stop();
                output.close();

                if (HPObjects.nonNull(inputStream)) {
                    inputStream.close();
                }

                if (HPObjects.nonNull(streamReader)) {
                    streamReader.close();
                }

                if (HPObjects.nonNull(bufferedReader)) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                onError.accept(e);
            }
        }
    }

    /**
     * Same as above, but ignore complete event.
     * @param args {@link String} command to be executed.
     * @param onNext {@link Consumer} instance to consume output.
     * @param onError {@link Consumer} instance to consume error.
     * @see #executeStream(String, Consumer, Consumer, Runnable)
     */
    public void executeStream(@NotNull String args,
                              @NotNull Consumer<String> onNext,
                              @NotNull Consumer<IOException> onError) {
        executeStream(args, onNext, onError, () -> {});
    }

    /**
     * Same as above, but ignore error/complete events.
     * @param args {@link String} command to be executed.
     * @param onNext {@link Consumer} instance to consume output.
     * @see #executeStream(String, Consumer, Consumer)
     */
    public void executeStream(@NotNull String args, @NotNull Consumer<String> onNext) {
        executeStream(args, onNext, HPLog::println);
    }

    /**
     * Same as above, but ignore all events.
     * @param args {@link String} command to execute.
     * @see #executeStream(String, Consumer)
     */
    public void executeStream(@NotNull String args) {
        executeStream(args, HPLog::println);
    }

    /**
     * Execute a command and continually deliver the output.
     * @param ARGS {@link String} command to be executed.
     * @return {@link Flowable} instance.
     * @see #executeStream(String, Consumer, Consumer, Runnable)
     */
    @NotNull
    public Flowable<String> rxa_executeStream(@NotNull final String ARGS) {
        final ProcessRunner THIS = this;

        return Flowable
            .<String>create(a ->
                THIS.executeStream(ARGS, a::onNext, a::onError, a::onComplete),
                BackpressureStrategy.BUFFER
            )
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .serialize();
    }
}
