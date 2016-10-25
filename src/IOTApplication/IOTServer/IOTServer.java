package IOTApplication.IOTServer;

import IOTApplication.*;
import IOTApplication.IOTClient.*;
import IOTApplication.IOTApplication.*;
import java.util.ArrayList;

/**
 * This class handles logical IOT requests.
 */
public class IOTServer implements IOTServerInterface {

    SubscriberList subscribers;
    IOTClientInterface client;
    IOTApplicationInterface application;

    IOTServer(SubscriberList pSubscribers, IOTClientInterface pClient, IOTApplicationInterface pApplication){

        subscribers = pSubscribers;
        client = pClient;
        application = pApplication;

    }

    public void broadcastServiceOffering(String typeOfService){

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
