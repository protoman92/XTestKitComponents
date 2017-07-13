package org.swiften.xtestkitcomponents.system;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.protocol.RetryProviderType;
import org.swiften.javautilities.rx.CustomTestSubscriber;
import org.swiften.javautilities.rx.HPReactives;
import org.swiften.javautilities.util.Constants;
import org.swiften.javautilities.util.HPLog;
import org.swiften.xtestkitcomponents.system.network.NetworkHandler;
import org.swiften.xtestkitcomponents.system.network.type.MaxPortType;
import org.swiften.xtestkitcomponents.system.network.type.NetworkHandlerErrorType;
import org.swiften.xtestkitcomponents.system.network.type.PortStepProviderType;
import org.swiften.xtestkitcomponents.system.network.type.PortProviderType;
import org.swiften.xtestkitcomponents.system.process.ProcessRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by haipham on 4/7/17.
 */
@SuppressWarnings("MessageMissingOnTestNGAssertion")
public final class NetworkHandlerTest implements NetworkHandlerErrorType {
    @NotNull private final NetworkHandler HANDLER;
    @NotNull private final ProcessRunner PROCESS_RUNNER;

    {
        HANDLER = spy(new NetworkHandler());

        /* Return this processRunner when we call HANDLER.processRunner() */
        PROCESS_RUNNER = spy(new ProcessRunner());
    }

    @BeforeMethod
    public void beforeMethod() {
        doReturn(PROCESS_RUNNER).when(HANDLER).processRunner();
    }

    @AfterMethod
    public void afterMethod() {
        reset(PROCESS_RUNNER, HANDLER);
        HANDLER.clearUsedPorts();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_checkPortWithError_shouldRerunUntilCorrect() {
        try {
            // Setup
            int tries = 10;
            doReturn(false).when(HANDLER).isPortAvailable(any(), anyInt());
            doReturn(true).when(HANDLER).isPortAvailable(any(), eq(tries));
            CheckPort param = new CheckPort(1);
            TestSubscriber subscriber = CustomTestSubscriber.create();

            // When
            HANDLER.rxa_checkUntilPortAvailable(param).subscribe(subscriber);
            subscriber.awaitTerminalEvent();

            // Then
            subscriber.assertSubscribed();
            subscriber.assertNoErrors();
            subscriber.assertComplete();
            assertEquals(HPReactives.firstNextEvent(subscriber), Integer.valueOf(tries));
            verify(HANDLER, times(tries)).isPortAvailable(any(), anyInt());
            verify(HANDLER, times(tries)).processRunner();
            verify(HANDLER, times(tries)).cmListAllPorts();
            verify(HANDLER, times(tries)).rxa_checkPortAvailable(any());
            verify(HANDLER).rxa_checkUntilPortAvailable(any());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_checkPortAvailable_shouldSucceed() {
        try {
            // Setup
            doReturn(Flowable.just("")).when(PROCESS_RUNNER).rxa_execute(any());
            CheckPort param = new CheckPort(0);
            TestSubscriber subscriber = CustomTestSubscriber.create();

            // When
            HANDLER.rxa_checkUntilPortAvailable(param).subscribe(subscriber);
            subscriber.awaitTerminalEvent();

            // Then
            subscriber.assertSubscribed();
            subscriber.assertNoErrors();
            subscriber.assertComplete();
            assertEquals(HPReactives.firstNextEvent(subscriber), Integer.valueOf(0));
            verify(HANDLER).processRunner();
            verify(HANDLER).cmListAllPorts();
            verify(HANDLER).rxa_checkPortAvailable(any());
            verify(HANDLER).rxa_checkUntilPortAvailable(any());
            verify(HANDLER).isPortAvailable(any(), anyInt());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_checkPortUntilAvailable_shouldSucceed() {
        // Setup
        int minPort = 4723, tries = 10;
        TestSubscriber subscriber = CustomTestSubscriber.create();

        // When
        Flowable.range(minPort, tries)
            .map(CheckPort::new)
            .flatMap(HANDLER::rxa_checkUntilPortAvailable)
            .doOnNext(a -> HPLog.printft("Port %d", a))
            .subscribe(subscriber);

        subscriber.awaitTerminalEvent();

        // Then
        Collection<Integer> used = HANDLER.usedPorts();
        Collection<Integer> distinct = used.stream().distinct().collect(Collectors.toList());
        subscriber.assertSubscribed();
        subscriber.assertNoErrors();
        subscriber.assertComplete();
        assertEquals(used.size(), distinct.size());
        assertEquals(used.size(), tries);
    }

    private static final class CheckPort implements PortProviderType, MaxPortType, PortStepProviderType, RetryProviderType {
        private final int PORT;

        CheckPort(int port) {
            PORT = port;
        }

        @Override
        public int retries() {
            return Constants.DEFAULT_RETRIES;
        }

        @Override
        public int port() {
            return PORT;
        }
    }
}
