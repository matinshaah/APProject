package GUI;

import Models.DepartmentChair;
import Models.User;

import java.time.LocalDateTime;

public class DCMenuBar extends TeacherMenuBar{
    DepartmentChair dc;

    DCMenuBar(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
    }

    @Override
    protected void initCom() {
        dc = (DepartmentChair)teacher;
        super.initCom();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        courseList.addActionListener(e -> new CoursesListPanel(user,lastLogin));
    }
}
