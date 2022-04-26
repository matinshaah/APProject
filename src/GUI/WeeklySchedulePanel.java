package GUI;

import Models.User;

import javax.swing.*;
import java.time.LocalDateTime;

public class WeeklySchedulePanel extends CoursesListPanel{
    public WeeklySchedulePanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        this.remove(filter);
        filteredCourses("none","",user.courses);
    }
}
