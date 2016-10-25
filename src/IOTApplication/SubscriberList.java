package IOTApplication;


import java.util.ArrayList;

/**
 * This class manages the subscribers, checks for duplicate subscribing requests and keeps the subscribers list up to date.
 */
public class SubscriberList {


    ArrayList<Subscriber> List;


    /**
     * Adds a new subscriber to the stored list of subscribers and checks for duplicates, which are overwritten in case the port number changed.
     * @param newSubscriber
     */
    public void addSubscriber(Subscriber newSubscriber){

        //Check if this Subscriber is already in the storage, if yes, delete the original entry
        for (Subscriber subscriber : List){
            if (subscriber.getIpAddress().equals(newSubscriber.getIpAddress())){
                List.remove(subscriber);
            }
        }

        //Add the new subscriber to the list
        List.add(newSubscriber);


    }
    public ArrayList<Subscriber> getSubscribers(){
        return List;
    }


}