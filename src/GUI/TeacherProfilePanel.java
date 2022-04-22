package GUI;

import Models.User;

import java.time.LocalDateTime;

public class TeacherProfilePanel extends UserMainPanel{
    public TeacherProfilePanel(User teacher, LocalDateTime loginTime) {
        super(teacher,loginTime);

    }
}
