package hackbgu.bgu.ac.il.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import hackbgu.bgu.ac.il.model.Course;
import hackbgu.bgu.ac.il.model.User;

public class MoodleServiceImpl implements MoodleService {
	private final MoodleRestClient restClient;
	private final SerializationUtils serializationUtils;
	public MoodleServiceImpl(){
		restClient = new MoodleRestClient();
		serializationUtils = new SerializationUtils();
	}
	
	public String listCoursesOfUser(String username) throws IOException {
		User user = getUserByUsername(username);
		return getCoursesOfUserById(user.Id);
	}

	
	public String getUser(String username) throws IOException {
		User user = getUserByUsername(username);
		String getCourses = MoodleOperation.listUserCourses.getOperation() + "&userid=" + user.Id;
		List<Course> serCourses = serializationUtils.deserialize(restClient.sendRest(getCourses), List.class);
		user.CourseIds = serCourses.stream().map(course -> course.Id).collect(Collectors.toList());
		return serializationUtils.serialize(user);
	}
	
	public String getAllUsers() throws IOException {
		String getUsers = MoodleOperation.getAllUsers.getOperation();
		return restClient.sendRest(getUsers);
	}

	private String getCoursesOfUserById(String userId) throws IOException {
		String getCourses = MoodleOperation.listUserCourses.getOperation() + "&userid=" + userId;
		return restClient.sendRest(getCourses);
	}
	
	private User getUserByUsername(String username) throws IOException {
		return serializationUtils.deserialize(restClient.sendRest(MoodleOperation.getUserByField.getOperation() + "&field=username&value="+username), User.class);
	}
}
