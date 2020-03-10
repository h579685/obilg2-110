package no.hvl.dat110.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	protected ConcurrentHashMap<String, List<Message>> offliners;
	
	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		offliners = new ConcurrentHashMap<String, List<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}
	//task E
	public void addOffliner(String user, Message msg) {
		if(offliners.containsKey(user)) {
			offliners.get(user).add(msg);
		} else {
			List<Message> msgs = new ArrayList<Message>();
			msgs.add(msg);
			offliners.put(user, msgs);
		}
	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		ClientSession cs = new ClientSession(user, connection);
		
		if(!clients.containsKey(user)) {
			clients.put(user, cs);
		}
	}

	public void removeClientSession(String user) {

		// TODO: remove client session for user from the storage

		if(clients.containsKey(user)) {
			clients.remove(user);
		}
		
	}

	public void createTopic(String topic) {

		// TODO: create topic in the storage

		if(!subscriptions.containsKey(topic)) {
			Set<String> subscribers = ConcurrentHashMap.newKeySet();
			subscriptions.put(topic, subscribers);
		}
	
	}

	public void deleteTopic(String topic) {

		// TODO: delete topic from the storage

		if(subscriptions.containsKey(topic)) {
			subscriptions.remove(topic);
		}
		
	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the topic
		
		if(subscriptions.containsKey(topic)) {
			Set<String> subscribers = subscriptions.get(topic);
			subscribers.add(user);
			subscriptions.replace(topic, subscribers);
		}
		
	}

	public void removeSubscriber(String user, String topic) {

		// TODO: remove the user as subscriber to the topic

		if(subscriptions.containsKey(topic)) {
			Set<String> subscribers = subscriptions.get(topic);
			if(subscribers.contains(user)) {
				subscribers.remove(user);
			}
			subscriptions.replace(topic, subscribers);			
		}
	}
}
