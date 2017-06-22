package org.swiften.xtestkitcomponents.lifecycle;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.rx.CustomTestSubscriber;
import org.swiften.javautilities.protocol.RetryType;

/**
 * Created by haipham on 8/6/17.
 */
public interface TestLifecycleType {
    /**
     * Execute this in {@link org.testng.annotations.BeforeClass}.
     * @param param {@link RetryType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_beforeClass(@NotNull RetryType param);

    /**
     * Execute this in {@link org.testng.annotations.AfterClass}.
     * @param param {@link RetryType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_afterClass(@NotNull RetryType param);

    /**
     * Execute this in {@link org.testng.annotations.BeforeMethod}.
     * @param param {@link RetryType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_beforeMethod(@NotNull RetryType param);

    /**
     * Execute this in {@link org.testng.annotations.AfterMethod}.
     * @param param {@link RetryType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_afterMethod(@NotNull RetryType param);

    /**
     * Convenience execution for {@link org.testng.annotations.BeforeClass}.
     * @param param {@link RetryType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_beforeClass(RetryType)
     */
    @SuppressWarnings("unchecked")
    default void beforeClass(@NotNull RetryType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_beforeClass(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Convenience execution for {@link org.testng.annotations.AfterClass}.
     * @param param {@link RetryType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_afterClass(RetryType)
     */
    @SuppressWarnings("unchecked")
    default void afterClass(@NotNull RetryType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_afterClass(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Convenience execution for {@link org.testng.annotations.BeforeMethod}.
     * @param param {@link RetryType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_beforeMethod(RetryType)
     */
    @SuppressWarnings("unchecked")
    default void beforeMethod(@NotNull RetryType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_beforeMethod(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Convenience execution for {@link org.testng.annotations.AfterMethod}.
     * @param param {@link RetryType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_afterMethod(RetryType)
     */
    @SuppressWarnings("unchecked")
    default void afterMethod(@NotNull RetryType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_afterMethod(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Verify that lifecycle methods have been correctly called.
     * @param subscriber {@link TestSubscriber} instance.
     */
    default void assertLifecycle(@NotNull TestSubscriber<?> subscriber) {
        subscriber.assertSubscribed();
        subscriber.assertNoErrors();
        subscriber.assertComplete();
    }
}
