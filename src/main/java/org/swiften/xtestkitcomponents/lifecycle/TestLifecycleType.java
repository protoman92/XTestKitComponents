package org.swiften.xtestkitcomponents.lifecycle;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.jetbrains.annotations.NotNull;
import org.swiften.javautilities.protocol.RetryProviderType;
import org.swiften.javautilities.rx.CustomTestSubscriber;

/**
 * Created by haipham on 8/6/17.
 */
public interface TestLifecycleType {
    /**
     * Execute this in {@link org.testng.annotations.BeforeClass}.
     * @param param {@link RetryProviderType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_beforeClass(@NotNull RetryProviderType param);

    /**
     * Execute this in {@link org.testng.annotations.AfterClass}.
     * @param param {@link RetryProviderType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_afterClass(@NotNull RetryProviderType param);

    /**
     * Execute this in {@link org.testng.annotations.BeforeMethod}.
     * @param param {@link RetryProviderType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_beforeMethod(@NotNull RetryProviderType param);

    /**
     * Execute this in {@link org.testng.annotations.AfterMethod}.
     * @param param {@link RetryProviderType} instance.
     * @return {@link Flowable} instance.
     */
    @NotNull Flowable<Boolean> rxa_afterMethod(@NotNull RetryProviderType param);

    /**
     * Convenience execution for {@link org.testng.annotations.BeforeClass}.
     * @param param {@link RetryProviderType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_beforeClass(RetryProviderType)
     */
    @SuppressWarnings("unchecked")
    default void beforeClass(@NotNull RetryProviderType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_beforeClass(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Convenience execution for {@link org.testng.annotations.AfterClass}.
     * @param param {@link RetryProviderType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_afterClass(RetryProviderType)
     */
    @SuppressWarnings("unchecked")
    default void afterClass(@NotNull RetryProviderType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_afterClass(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Convenience execution for {@link org.testng.annotations.BeforeMethod}.
     * @param param {@link RetryProviderType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_beforeMethod(RetryProviderType)
     */
    @SuppressWarnings("unchecked")
    default void beforeMethod(@NotNull RetryProviderType param) {
        TestSubscriber subscriber = CustomTestSubscriber.create();
        rxa_beforeMethod(param).subscribe(subscriber);
        subscriber.awaitTerminalEvent();
        assertLifecycle(subscriber);
    }

    /**
     * Convenience execution for {@link org.testng.annotations.AfterMethod}.
     * @param param {@link RetryProviderType} instance.
     * @see #assertLifecycle(TestSubscriber)
     * @see #rxa_afterMethod(RetryProviderType)
     */
    @SuppressWarnings("unchecked")
    default void afterMethod(@NotNull RetryProviderType param) {
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
