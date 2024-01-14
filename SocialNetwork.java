import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class SocialNetwork {
	private HashMap<String, Person> peopleNetwork;
	private HashMap<Person, HashSet<Person>> friendships;

	public SocialNetwork() {
		this.peopleNetwork = new HashMap<>();
		this.friendships = new HashMap<>();

	}

	public void addPersonToTheNetwork(Person person) throws NullParameterException, PersonAlreadyInTheNetworkException {
		if (person == null) {
			throw new NullParameterException("Person can not be added to the network as they are null");
		}
		if (peopleNetwork.containsKey(person.getUsername())) {
			throw new PersonAlreadyInTheNetworkException(
					"Person can not be added to the network as they already are in the network");
		}
		peopleNetwork.put(person.getUsername(), person);
		friendships.put(person, new HashSet<>());
	}

	public void removePersonFromTheNetwork(Person person)
			throws NullParameterException, PersonNotInTheNetworkException {
		if (person == null) {
			throw new NullParameterException("Person can not be added to the network as they are null");
		}
		if (!peopleNetwork.containsKey(person.getUsername())) {
			throw new PersonNotInTheNetworkException(
					"Person can not be removed from the network because they are not in the network");
		}
		for (HashSet<Person> friendList : friendships.values()) {
			friendList.remove(person);
		}

		peopleNetwork.remove(person.getUsername());
		friendships.remove(person);
	}

	public void addFriend(Person person1, Person person2) throws NullParameterException, PersonNotInTheNetworkException,
			ParametersAreEqualException, FriendshipAlreadyExistsException {
		if (person1 == null || person2 == null) {
			throw new NullParameterException("Persons can not be null");
		}
		if (!peopleNetwork.containsKey(person1.getUsername()) || !peopleNetwork.containsKey(person2.getUsername())) {
			throw new PersonNotInTheNetworkException(
					"Persons can not be friends if any of them are not in the network");
		}
		if (person1.equals(person2)) {
			throw new ParametersAreEqualException("Person can not add as a friend themselves");
		}
		if (friendships.get(person1).contains(person2)) {
			throw new FriendshipAlreadyExistsException(
					"Person can not add a friend a person it already has that person as a friend");
		}
		friendships.get(person1).add(person2);
		friendships.get(person2).add(person1);
	}

	public void removeFriend(Person person1, Person person2) throws NullParameterException,
			PersonNotInTheNetworkException, ParametersAreEqualException, FriendshipAlreadyExistsException {
		if (person1 == null || person2 == null) {
			throw new NullParameterException("Persons can not be null");
		}
		if (!peopleNetwork.containsKey(person1.getUsername()) || !peopleNetwork.containsKey(person2.getUsername())) {
			throw new PersonNotInTheNetworkException(
					"Persons can not remove friends if  of any of them are not in the network");
		}
		if (person1.equals(person2)) {
			throw new ParametersAreEqualException("Person can not remove themselves as a friend");
		}
		if (!friendships.get(person1).contains(person2)) {
			throw new FriendshipAlreadyExistsException(
					"Person can not remove a friend a person it does not have as a friend");
		}
		friendships.get(person1).remove(person2);
		friendships.get(person2).remove(person1);
	}

	public int numberOfFriends(Person person) throws NullParameterException, PersonNotInTheNetworkException {
		if (person == null) {
			throw new NullParameterException("Person can not be null");
		}
		if (!peopleNetwork.containsKey(person.getUsername())) {
			throw new PersonNotInTheNetworkException(
					"Person can not view the number of friends if they are not in the network");
		}
		return friendships.get(person).size();

	}

	// Method to find the friends
	public HashSet<Person> findFriendships(Person person) {
		HashSet<Person> friendshipSet = friendships.get(person);
		if (friendshipSet != null) {
			return friendshipSet;
		} else {
			return new HashSet<>();
		}

	}

	public HashSet<Person> recommendFriends(Person person)
	        throws NullParameterException, PersonNotInTheNetworkException {
	    if (person == null) {
	        throw new NullParameterException("Person can not be null");
	    }
	    if (!peopleNetwork.containsKey(person.getUsername())) {
	        throw new PersonNotInTheNetworkException(
	                "Person can not use the method recommendFriends if they are not in the network");
	    }
	    HashSet<Person> recommendedPersonsList = new HashSet<>();
	    HashSet<Person> visited = new HashSet<>();
	    HashSet<Person> friends = findFriendships(person);
	    Queue<Person> queue = new LinkedList<>(friends);
	    visited.addAll(friends);
	    // Perform the BFS traversal
	    while (!queue.isEmpty()) {
	        int levelSize = queue.size();
	        for (int i = 0; i < levelSize; i++) {
	            Person currentPerson = queue.poll();
	            // Iterate through friends of the current person
	            for (Person recommendedPerson : findFriendships(currentPerson)) {
	                // Check if the recommended person is not visited and has a common friend with the user we recommend to
	                if (!visited.contains(recommendedPerson) && haveCommonFriend(person, recommendedPerson)) {
	                    queue.offer(recommendedPerson);
	                    visited.add(recommendedPerson);
	                    // Check if the recommendedPerson and the user we recommend to have any common interest and a small age difference
	                    if (Math.abs(person.getAge() - recommendedPerson.getAge()) <= 5 &&
	                            haveCommonInterest(person, recommendedPerson) && !recommendedPerson.equals(person)) {
	                        // Add it to the recommended list
	                        recommendedPersonsList.add(recommendedPerson);
	                    }
	                }
	            }
	        }
	    }
	    return recommendedPersonsList;
	}


	private boolean haveCommonInterest(Person person1, Person person2) {
		Set<String> interests1 = person1.getInterests();
		Set<String> interests2 = person2.getInterests();
		// Check if there are any common interests
		for (String interest : interests1) {
			if (interests2.contains(interest)) {
				return true;
			}
		}
		return false;
	}

	private boolean haveCommonFriend(Person person1, Person person2) {
		Set<Person> friends1 = findFriendships(person1);
		Set<Person> friends2 = findFriendships(person2);
		// Check if there are any common friends
		for (Person friend : friends1) {
			if (friends2.contains(friend)) {
				return true;
			}
		}

		return false;
	}

	public HashMap<String, Person> getPeopleNetwork() {
		return peopleNetwork;
	}

	public HashMap<Person, HashSet<Person>> getFriendships() {
		return friendships;
	}

	public Person getPersonInNetwork(String username) {
		return peopleNetwork.get(username);
	}

}
