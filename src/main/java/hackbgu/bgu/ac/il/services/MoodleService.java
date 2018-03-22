package hackbgu.bgu.ac.il.services;

import java.io.IOException;

public interface MoodleService {
	String listCoursesOfUser(String username) throws IOException, Exception;
}
