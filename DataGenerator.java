import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DataGenerator {
	private static final int[] AGES = { 16, 18, 20, 22, 24, 26, 38, 42, 46, 50, 54, 58, 62, 66, 70, 74, 78, 82, 86,
			90 };

	public static void generateUsersWithFriendships(int numberOfUsers, SocialNetwork network) {
		List<Person> users = generateUsers(numberOfUsers);

		// Add the users to the social network
		addUsersToNetwork(users, network);

		// Add friendships to the users
		addFriendships(users, network, 10);

	}

	private static List<Person> generateUsers(int numberOfUsers) {
		List<Person> users = new ArrayList<>();
		List<String> allInterests = allInterests();
		int AgeIndex = 0;

		// Create users with random subsets of interests
		for (int i = 1; i <= numberOfUsers; i++) {
			String username = "user" + i;
			String location = "Location" + i;
			int age = AGES[AgeIndex];
			Set<String> interests = generateRandomInterests(allInterests);
			Person user = new Person(username, location, age, interests);
			users.add(user);
			AgeIndex = (AgeIndex + 1) % AGES.length;
		}

		return users;
	}

	private static void addUsersToNetwork(List<Person> users, SocialNetwork network) {
		for (Person user : users) {
			try {
				network.addPersonToTheNetwork(user);
			} catch (NullParameterException | PersonAlreadyInTheNetworkException e) {
				e.printStackTrace();
			}
		}
	}

	private static void addFriendships(List<Person> users, SocialNetwork network, int numberOfFriends) {
		for (int i = 0; i < users.size() - numberOfFriends; i++) {
			Person user = users.get(i);
			for (int j = 1; j <= numberOfFriends; j++) {
				Person friend = users.get(i + j);
				try {
					network.addFriend(user, friend);
				} catch (NullParameterException | PersonNotInTheNetworkException | ParametersAreEqualException
						| FriendshipAlreadyExistsException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static List<String> allInterests() {
		List<String> interests = new ArrayList<>();
		interests.add("Reading");
		interests.add("Programming");
		interests.add("Sports");
		interests.add("Traveling");
		interests.add("Cooking");
		interests.add("Movies");
		interests.add("Football");
		interests.add("Basketball");
		interests.add("Dancing");
		interests.add("Playing");
		return interests;
	}

	private static Set<String> generateRandomInterests(List<String> allInterests) {
		Set<String> interests = new HashSet<>();
		int numberOfInterests = new Random().nextInt(allInterests.size()) + 1;
		List<String> shuffledInterests = new ArrayList<>(allInterests);
		Collections.shuffle(shuffledInterests);
		for (int i = 0; i < numberOfInterests; i++) {
			interests.add(shuffledInterests.get(i));
		}
		return interests;
	}

}
