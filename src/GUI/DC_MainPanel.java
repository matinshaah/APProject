package GUI;

import Models.DepartmentChair;
import Models.Teacher;

import java.time.LocalDateTime;

public class DC_MainPanel extends TeacherMainPanel{
    DepartmentChair dc;
    public DC_MainPanel(Teacher teacher, LocalDateTime loginTime) {
        super(teacher,loginTime);
    }

    @Override
    protected void initCom() {
        dc = (DepartmentChair) teacher;
        super.initCom();
    }
}
