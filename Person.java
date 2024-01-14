
import java.util.Set;
import java.util.HashSet;

public class Person {
	private String username;
	private String location;
	private int age;
	private Set<String> interests;

	// Constructor with the set of interest as a parameter used in for in dataGenerator class
	public Person(String username, String location, int age, Set<String> interests) {
		this.username = username;
		this.location = location;
		this.age = age;
		this.interests = interests;
	}

	// Construction without the set of interest as a parameter
	public Person(String username, String location, int age) {
		this.username = username;
		this.location = location;
		this.age = age;
		this.interests = new HashSet<>();
	}

	public void addInterest(String interest) throws NullParameterException, InterestAlreadyAddedException {
		if (interest == null) {
			throw new NullParameterException("Intrerest can not be added as it is null");
		}
		if (interests.contains(interest)) {
			throw new InterestAlreadyAddedException(
					"This interest can not be added as it already exists in the list of interests the user has");
		}
		interests.add(interest);
	}

	public void removeInterest(String interest) throws NullParameterException, InterestNotAddedException {
		if (interest == null) {
			throw new NullParameterException("Intrerest can not be added as it is null");
		}
		if (!interests.contains(interest)) {
			throw new InterestNotAddedException(
					"This interest can not be removed as it does not exists in the list of interests the user has");
		}

		interests.remove(interest);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setInterests(Set<String> interests) {
		this.interests = interests;
	}

	public String getLocation() {
		return location;
	}

	public int getAge() {
		return age;
	}

	public Set<String> getInterests() {
		return interests;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "Person [username=" + username + ", location=" + location + ", age=" + age + ", interests=" + interests
				+ "]";
	}

}