package org.swiften.xtestkitcomponents.system.network.type;

/**
 * Created by haipham on 5/18/17.
 */

import io.reactivex.Flowable;
import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.bool.HPBooleans;
import org.swiften.javautilities.protocol.RetryProviderType;
import org.swiften.javautilities.string.HPStrings;
import org.swiften.xtestkitcomponents.system.network.param.GetProcessNameParam;
import org.swiften.xtestkitcomponents.system.process.ProcessRunner;
import org.swiften.xtestkitcomponents.system.process.ProcessRunnerHolderType;

import java.util.function.Predicate;

/**
 * This interface provides methods to handle network/system processes.
 */
public interface NetworkHandlerType extends ProcessRunnerHolderType, NetworkHandlerErrorType {
    /**
     * Command to kill all instances of a process name.
     * @param name {@link String} value.
     * @return {@link String} value.
     */
    @NotNull
    default String cmKillAll(@NotNull String name) {
        return String.format("killall %s", name);
    }

    /**
     * Command to list all used ports.
     * @return {@link String} value.
     */
    @NotNull
    default String cmListAllPorts() {
        return "lsof -i";
    }

    /**
     * Command to get a PID process that is listing to a port.
     * @param port {@link Integer} value.
     * @return {@link String} value.
     */
    @NotNull
    default String cmFindPID(int port) {
        return String.format("lsof -t -i:%d", port);
    }

    /**
     * Command to stop a PID process.
     * @param pid The PID the process. {@link String} value.
     * @return {@link String} value.
     */
    @NotNull
    default String cmKillPID(@NotNull String pid) {
        return String.format("kill %s", pid);
    }

    /**
     * Command to find a process PID using its name.
     * @param name The name of the process. {@link String} value.
     * @return {@link String} value.
     */
    @NotNull
    default String cmFindPID(@NotNull String name) {
        return String.format("pgrep %s", name);
    }

    /**
     * Find process name using its PID.
     * @param pid {@link String} value.
     * @return {@link String} value.
     */
    @NotNull
    default String cmFindProcessName(@NotNull String pid) {
        return String.format("ps -p %s -o comm=", pid);
    }

    /**
     * Get a process' name using its PID value.
     * @param param {@link T} instance.
     * @param <T> Generics parameter.
     * @return {@link Flowable} instance.
     * @see #processRunner()
     * @see #cmFindProcessName(String)
     * @see ProcessRunner#rxa_executeStream(String)
     * @see T#pid()
     * @see T#retries()
     */
    @NotNull
    default <T extends PIDProviderType & RetryProviderType> Flowable<String>
    rxa_getProcessName(@NotNull T param) {
        ProcessRunner runner = processRunner();
        String command = cmFindProcessName(param.pid());
        return runner.rxa_execute(command).retry(param.retries());
    }

    /**
     * Kill a process using its PID value.
     * @param pid {@link String} value.
     * @return {@link Flowable} instance.
     * @see #processRunner()
     * @see #cmKillPID(String)
     * @see #NO_SUCH_PROCESS
     */
    @NotNull
    default Flowable<Boolean> rxa_killWithPID(@NotNull String pid) {
        ProcessRunner runner = processRunner();
        String command = cmKillPID(pid);

        return runner.rxa_execute(command)
            .onErrorResumeNext(t -> {
                /* If 'No such process' is thrown, we skip the error */
                if (t.getMessage().contains(NO_SUCH_PROCESS)) {
                    return Flowable.empty();
                }

                return Flowable.error(t);
            })
            .map(HPBooleans::toTrue)
            .defaultIfEmpty(true);
    }

    /**
     * Kill a process that is listening to a port.
     * @param PARAM {@link T} instance.
     * @param NP {@link Predicate} instance that checks whether a process
     *           should be terminated. It accepts {@link String} that
     *           represents the process' name.
     * @param <T> Generics parameter.
     * @return {@link Flowable} instance.
     * @see #processRunner()
     * @see ProcessRunner#rxa_executeStream(String)
     * @see HPStrings#isNotNullOrEmpty(String)
     * @see #rxa_getProcessName(PIDProviderType)
     * @see T#port()
     * @see T#retries()
     */
    @NotNull
    default  <T extends RetryProviderType & PortProviderType>
    Flowable<Boolean> rxa_killWithPort(@NotNull final T PARAM,
                                       @NotNull final Predicate<String> NP) {
        return processRunner()
            .rxa_execute(cmFindPID(PARAM.port()))
            .filter(HPStrings::isNotNullOrEmpty)
            .map(a -> a.split("\n"))
            .flatMap(Flowable::fromArray)
            .map(a -> GetProcessNameParam.builder()
                .withPID(a)
                .withRetryProvider(PARAM)
                .build())

            /* Here we have the opportunity to check whether a process can
             * be killed. This can be useful when lsof returns multiple PIDs,
             * one or more of which we do not want to kill */
            .flatMap(gp -> this
                .rxa_getProcessName(gp)
                .filter(NP::test)
                .flatMap(a -> this.rxa_killWithPID(gp.pid())))
            .defaultIfEmpty(true)
            .retry(PARAM.retries())
            .onErrorReturnItem(true)
            .all(HPBooleans::isTrue)
            .toFlowable();
    }

    /**
     * Kill a process using its name.
     * @param name {@link String} value.
     * @return {@link Flowable} instance.
     * @see #processRunner()
     * @see ProcessRunner#rxa_executeStream(String)
     * @see #cmFindPID(String)
     * @see #rxa_killWithPID(String)
     * @see HPBooleans#isTrue(boolean)
     */
    @NotNull
    default Flowable<Boolean> rxa_killWithName(@NotNull String name) {
        return processRunner()
            .rxa_execute(cmFindPID(name))
            .filter(HPStrings::isNotNullOrEmpty)
            .map(a -> a.split("\n"))
            .flatMap(Flowable::fromArray)
            .flatMap(this::rxa_killWithPID)
            .defaultIfEmpty(true)
            .onErrorReturnItem(true)
            .all(HPBooleans::isTrue)
            .toFlowable();
    }

    /**
     * Kill all instances of a process.
     * @param name The process' name. {@link String} value.
     * @return {@link Flowable} instance.
     * @see #processRunner()
     * @see ProcessRunner#rxa_executeStream(String)
     * @see #cmKillAll(String)
     * @see HPBooleans#toTrue(Object)
     */
    @NotNull
    default Flowable<Boolean> rxa_killAll(@NotNull String name) {
        return processRunner().rxa_execute(cmKillAll(name)).map(HPBooleans::toTrue);
    }
}
