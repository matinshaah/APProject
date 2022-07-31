package models;

public enum Grade {
    BS,
    MS,
    PHD;
    public static Grade getGradeByName(String grade){
        for (Grade g :
                Grade.values()) {
            if(g.toString().equalsIgnoreCase(grade))
                return g;
        }
        return null;
    }
}
