
import java.util.HashSet;
import java.util.List;

public class SocialNetworkTester {

	public static void main(String[] args) throws NullParameterException, PersonNotInTheNetworkException {

		SocialNetwork socialNetwork = new SocialNetwork();

		// Create Person objects
		Person person1 = new Person("Rron", "Location1", 25);
		Person person2 = new Person("Jon", "Location2", 30);
		Person person3 = new Person("Lum", "Location3", 22);
		Person person4 = new Person("Don", "Location4", 27);
		Person person5 = new Person("Bora", "Location5", 21);
		Person person6 = new Person("Dardan", "Location6", 28);
		Person person7 = new Person("Ismail", "Location7", 30);
		Person person8 = new Person("Donald", "Location8", 25);

		try {
			// Code inserted to test if the methods are working
			socialNetwork.addPersonToTheNetwork(person1);
			socialNetwork.addPersonToTheNetwork(person2);
			socialNetwork.addPersonToTheNetwork(person3);
			socialNetwork.addPersonToTheNetwork(person4);
			socialNetwork.addPersonToTheNetwork(person5);
			socialNetwork.addPersonToTheNetwork(person6);
			socialNetwork.addPersonToTheNetwork(person7);
			socialNetwork.addPersonToTheNetwork(person8);
			person1.addInterest("Reading");
			person1.addInterest("Programming");
			person2.addInterest("Sports");
			person2.addInterest("Travelling");
			person3.addInterest("Cooking");
			person3.addInterest("Movies");
			person4.addInterest("Football");
			person4.addInterest("Basketball");
			person5.addInterest("Programming");
			person5.addInterest("Movies");
			person6.addInterest("Reading");
			person6.addInterest("Basketball");
			person7.addInterest("Programming");
			person7.addInterest("Movies");
			person8.addInterest("Reading");
			person8.addInterest("Programming");
			socialNetwork.addFriend(person1, person2);
			socialNetwork.addFriend(person1, person3);
			socialNetwork.addFriend(person1, person4);
			socialNetwork.addFriend(person4, person6);
			socialNetwork.addFriend(person4, person7);
			socialNetwork.addFriend(person3, person5);
			displayUserFriends(person1, socialNetwork);
			displayUserFriends(person3, socialNetwork);
			displayUserFriends(person2, socialNetwork);
			displayUserFriends(person4, socialNetwork);
			displayRecommendedFriends(person1, socialNetwork);
			socialNetwork.removePersonFromTheNetwork(person3);
			// Now the friend list of person 1 should
			displayUserFriends(person1, socialNetwork);
			displayUserProfile(person1, socialNetwork);
			displayRecommendedFriends(person1, socialNetwork);
			person7.setAge(31);
			// Person 7 should not be recommended anymore
			displayRecommendedFriends(person1, socialNetwork);

			// Testing code
			DataGenerator.generateUsersWithFriendships(100, socialNetwork);
			long startTime = System.currentTimeMillis();
			System.out.println(socialNetwork.recommendFriends(socialNetwork.getPersonInNetwork("user1")));
			long endTime = System.currentTimeMillis();
			System.out.println("Execution Time: " + (endTime - startTime) + " milliseconds");

		} catch (NullParameterException | PersonAlreadyInTheNetworkException | InterestAlreadyAddedException
				| ParametersAreEqualException | FriendshipAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

// Helper method to display user profile
	private static void displayUserProfile(Person person, SocialNetwork network)
			throws NullParameterException, PersonNotInTheNetworkException {
		System.out.println("User Profile:");
		System.out.println("Username: " + person.getUsername());
		System.out.println("Location: " + person.getLocation());
		System.out.println("Age: " + person.getAge());
		System.out.println("Interests: " + person.getInterests());
		System.out.println("Number of friends " + network.numberOfFriends(person));
		System.out.println("Friends of " + person.getUsername() + ":");
		HashSet<Person> friendshipSet = network.findFriendships(person);
		for (Person friend : friendshipSet) {
			System.out.println(friend.getUsername());
		}

	}

	// Helper method to display user friends
	private static void displayUserFriends(Person person, SocialNetwork network) {
		System.out.println("Friends of " + person.getUsername() + ":");
		HashSet<Person> friendshipSet = network.findFriendships(person);
		for (Person friend : friendshipSet) {
			System.out.println(friend.getUsername());
		}

	}

	// Helper method to display recommended friend
	private static void displayRecommendedFriends(Person person, SocialNetwork network)
			throws NullParameterException, PersonNotInTheNetworkException {
		System.out.println("Recommended Friends for " + person.getUsername() + ":");
		HashSet<Person> recommendedFriends = network.recommendFriends(person);

		if (recommendedFriends.isEmpty()) {
			System.out.println("No recommended friends found.");
		} else {
			recommendedFriends.forEach(friend -> System.out.println("- " + friend.getUsername()));
		}

		System.out.println();
	}
}
