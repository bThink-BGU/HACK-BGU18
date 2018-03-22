package hackbgu.bgu.ac.il.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hackbgu.bgu.ac.il.model.Course;
import hackbgu.bgu.ac.il.model.Courses;
import hackbgu.bgu.ac.il.model.User;

public class MoodleServiceImpl implements MoodleService {
	private final MoodleRestClient restClient;
	private final SerializationUtils serializationUtils;
	public MoodleServiceImpl(){
		restClient = new MoodleRestClient();
		serializationUtils = new SerializationUtils();
	}
	
	public String listCoursesOfUser(String username) throws Exception {
		User user = getUserByUsername(username);
		return getCoursesOfUserById(user.id);
	}

	public String getUser(String username) throws Exception {
		User user = getUserByUsername(username);
		String getCourses = MoodleOperation.listUserCourses.getOperation() + "&userid=" + user.id;
		List<Course> serCourses = ((List<Course>)serializationUtils.deserializeGeneric(restClient.sendRest(getCourses), TypeReferenceSerializationHelper.class, List.class, Course.class));
		user.courseIds = serCourses.stream().map(course -> course.id).collect(Collectors.toList());
		return serializationUtils.serialize(user);
	}
	
	public String getCourse(String courseId) throws Exception {
		String getCourses = MoodleOperation.getCourses.getOperation();
		String response = restClient.sendRest(getCourses);
		List<Course> allCourses = (serializationUtils.deserialize(response, Courses.class)).courses;
		Optional<Course> course = allCourses.stream().filter(c->c.id.equals(courseId)).findFirst();
		if (!course.isPresent()){
			return null;
		}
		return serializationUtils.serialize(course.get());
	}
	
	public static void main(String[] args) throws Exception{
		MoodleServiceImpl service = new MoodleServiceImpl();
		System.out.println(service.getCourse("3"));
	}
	
	public String getAllUsers() throws IOException {
		String getUsers = MoodleOperation.getAllUsers.getOperation();
		return restClient.sendRest(getUsers);
	}

	private String getCoursesOfUserById(String userId) throws IOException {
		String getCourses = MoodleOperation.listUserCourses.getOperation() + "&userid=" + userId;
		return restClient.sendRest(getCourses);
	}
	
	private User getUserByUsername(String username) throws Exception {
		String user = restClient.sendRest(MoodleOperation.getUserByField.getOperation() + "&field=username&values%5B0%5D="+username);
		return ((List<User>)serializationUtils.deserializeGeneric(user, TypeReferenceSerializationHelper.class, List.class, User.class)).get(0);
	}
}
