package IOTApplication.IOTServer;

import IOTApplication.*;
import IOTApplication.IOTClient.*;
import IOTApplication.IOTApplication.*;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * This class handles logical IOT requests.
 */
public class IOTServer implements IOTServerInterface {

    private SubscriberList subscribers;
    private IOTClientInterface client;
    private IOTApplicationInterface application;

    public IOTServer(SubscriberList pSubscribers, IOTClientInterface pClient, IOTApplicationInterface pApplication){

        subscribers = pSubscribers;
        client = pClient;
        application = pApplication;

    }

    public void receiveServiceOffering(InetAddress sourceAddress, String data) {
        System.out.println("Received data from " + sourceAddress);
        //TODO: compare data to internal list of serviceOfferings
        //TODO: if interested, tell Client to send subscriptionRequest to sourceAddress
        // else ignore message and move on :)
    }

    /**
     * This method creates a new subscriber and sets its ip and port attributes, and hands it over to the Subscriber list for storing.
     * @param destinationIP
     * @param destinationPort
     */
    public void subscribeRequestHandler(String destinationIP, int destinationPort){

        //Create a new subscriber
        Subscriber newSubscriber = new Subscriber(destinationIP, destinationPort);
        subscribers.addSubscriber(newSubscriber);
    }

    public void incomingNotificationHandler(String messageType, ArrayList notification){

    }
}
