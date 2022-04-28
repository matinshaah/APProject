package GUI;

import Models.Course;
import Models.User;


import java.time.LocalDateTime;

public class WeeklySchedulePanel extends CoursesListPanel{
    public WeeklySchedulePanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        this.remove(filter);
        filteredCourses("term", Course.CurrentTerm+"",user.courses);
    }
}
