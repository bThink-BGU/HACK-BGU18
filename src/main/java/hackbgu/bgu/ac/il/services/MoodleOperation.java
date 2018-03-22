package hackbgu.bgu.ac.il.services;

public enum MoodleOperation {
	listUserCourses("core_enrol_get_users_courses"),
	getCourses("core_course_get_courses_by_field"),
	getUserByField("core_user_get_users_by_field"),
	getAllUsers("core_user_get_users");
	
	private String operation;
	private MoodleOperation(String op){
		this.operation = op;
	}

	public String getOperation() {
		return operation;
	}
}
