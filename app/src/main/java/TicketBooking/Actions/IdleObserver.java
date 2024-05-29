package TicketBooking.Actions;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.util.Duration;

public class IdleObserver {
    private Timeline idleTimeline ;
    private EventHandler<Event> userEventHandler;

    public IdleObserver(Duration idleTime, Runnable notifier, boolean startMonitoring) {
        idleTimeline = new Timeline(new KeyFrame(idleTime, e -> notifier.run()));
        idleTimeline.setCycleCount(Animation.INDEFINITE);
        userEventHandler = e -> notIdle() ; 
        if (startMonitoring) {
            startMonitoring();
        }
    }
    public IdleObserver(Duration idleTime, Runnable notifier) {
        this(idleTime, notifier, false);
    }
    public void register(Scene scene, EventType<? extends Event> eventType) {
        System.out.println("Scene: Registed Observer");
        scene.addEventFilter(eventType, userEventHandler);
    }
    public void register(Node node, EventType<? extends Event> eventType) {
        System.out.println("Node: Registed Observer");
        node.addEventFilter(eventType, userEventHandler);
    }
    public void unregister(Scene scene, EventType<? extends Event> eventType) {
        scene.removeEventFilter(eventType, userEventHandler);
    }
    public void unregister(Node node, EventType<? extends Event> eventType) {
        node.removeEventFilter(eventType, userEventHandler);
    }
    public void notIdle() {
        if (idleTimeline.getStatus() == Animation.Status.RUNNING) {
            System.out.println("Not idle");
            idleTimeline.playFromStart();
        } else {
            System.out.println("Idle");
        }
    }
    public void startMonitoring() {
        System.out.println("Start Monitoring");
        idleTimeline.playFromStart();
    }
    public void stopMonitoring() {
        System.out.println("Stop Monitoring");
        idleTimeline.stop();
    }
}