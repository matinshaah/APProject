package GUI;

import Models.User;

import java.time.LocalDateTime;

public class StudentProfilePanel extends StudentMainPanel {

    public StudentProfilePanel(User student, LocalDateTime loginTime) {
        super(student, loginTime);
    }


    @Override
    protected void setMainChart() {
    }
}
