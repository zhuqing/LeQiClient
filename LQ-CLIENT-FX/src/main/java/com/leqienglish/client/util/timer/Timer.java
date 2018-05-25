/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.timer;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * A timer class in the spirit of java.swing.Timer but using JavaFX properties.
 *
 * @author Tom Eugelink
 *
 */
public class Timer {
	// ==================================================================================================================
    // CONSTRUCTOR

    /**
     *
     * @param runnable
     */
    public Timer(Runnable runnable) {
        this(true, runnable);
    }

    /**
     *
     * @param isDaemon
     * @param runnable
     */
    public Timer(boolean isDaemon, final Runnable runnable) {
        this.runnable = runnable;
        timer = new java.util.Timer(isDaemon);
    }
    final private Runnable runnable;
    final private java.util.Timer timer;

	// ==================================================================================================================
    // PROPERTIES
    /**
     * delay: initial delay
     */
    public ObjectProperty<Duration> delayProperty() {
        return this.delayObjectProperty;
    }
    final private ObjectProperty<Duration> delayObjectProperty = new SimpleObjectProperty<>(this, "delay", Duration.millis(0));

    /**
     *
     * @return
     */
    public Duration getDelay() {
        return this.delayObjectProperty.getValue();
    }

    public void setDelay(Duration value) {
        this.delayObjectProperty.setValue(value);
    }

    public Timer withDelay(Duration value) {
        setDelay(value);
        return this;
    }

    /**
     * cycleDuration: time between fires
     */
    public ObjectProperty<Duration> cycleDurationProperty() {
        return this.cycleDurationObjectProperty;
    }
    final private ObjectProperty<Duration> cycleDurationObjectProperty = new SimpleObjectProperty<>(this, "cycleDuration", Duration.millis(1000));

    public Duration getCycleDuration() {
        return this.cycleDurationObjectProperty.getValue();
    }

    public void setCycleDuration(Duration value) {
        this.cycleDurationObjectProperty.setValue(value);
    }

    public Timer withCycleDuration(Duration value) {
        setCycleDuration(value);
        return this;
    }

    /**
     * repeats: If flag is false, instructs the Timer to send only one action
     * event to its listeners.
     */
    public ObjectProperty<Boolean> repeatsProperty() {
        return this.repeatsObjectProperty;
    }
    final private ObjectProperty<Boolean> repeatsObjectProperty = new SimpleObjectProperty<>(this, "repeats", Boolean.TRUE);

    public boolean getRepeats() {
        return this.repeatsObjectProperty.getValue();
    }

    public void setRepeats(boolean value) {
        this.repeatsObjectProperty.setValue(value);
    }

    /**
     *
     * @param value
     * @return
     */
    public Timer withRepeats(boolean value) {
        setRepeats(value);
        return this;
    }

	// ==================================================================================================================
    // TIMER
    /**
     * Start the timer
     *
     * @return
     */
    synchronized public Timer start() {
        // check if the timer is already running
        if (timerTaskAtomicReference.get() != null) {
            throw new IllegalStateException("Timer already started");
        }

        // create a task and schedule it
        final TimerTask lTimerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(runnable);
                if (repeatsObjectProperty.getValue().booleanValue() == false) {
                    stop();
                }
            }
        };
        timer.schedule(lTimerTask, (long) this.delayObjectProperty.getValue().toMillis(), (long) this.cycleDurationObjectProperty.getValue().toMillis());

        // remember for future reference
        timerTaskAtomicReference.set(lTimerTask);

        // for chaining
        return this;
    }
    final private AtomicReference<TimerTask> timerTaskAtomicReference = new AtomicReference<>(null);

    /**
     * stop the timer if running
     *
     * @return
     */
    public Timer stop() {
        TimerTask lTimerTask = timerTaskAtomicReference.getAndSet(null);
        if (lTimerTask != null) {
            lTimerTask.cancel();
        }

        // for chaining
        return this;
    }

    /**
     * restart the timer
     */
    public Timer restart() {
        stop();
        start();

        // for chaining
        return this;
    }
}
