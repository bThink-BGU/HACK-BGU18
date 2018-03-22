package hackbgu.bgu.ac.il.services;

import java.io.IOException;

public class MoodleServiceImpl implements MoodleService {
	private final MoodleRestClient restClient;
	public MoodleServiceImpl(){
		restClient = new MoodleRestClient();
	}
	
	public String listCoursesOfUser(String username) throws IOException {
		String userId = MoodleOperation.getUserByField.getOperation() + "&field=username&value="+username;
		String getCourses = MoodleOperation.listUserCourses.getOperation() + "&userid=" + userId;
		return restClient.sendRest(getCourses);
	}
}
