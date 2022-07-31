package gui;

import models.User;

import java.time.LocalDateTime;

public class DepStudentScorePanel extends StudentTempScoresPanel{
    public DepStudentScorePanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        this.remove(recordObjections);
        this.remove(idField);
        this.remove(idLabel);
    }
}
