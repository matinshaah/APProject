package gui;

import models.User;

import java.time.LocalDateTime;

public class DepTeacherScorePanel extends TeacherTempScorePanel{
    public DepTeacherScorePanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        this.remove(recordObjectionsAnswer);
        this.remove(finalRegistration);
        this.remove(tempRegistration);
        this.remove(idField);
        this.remove(idLabel);
    }
}
