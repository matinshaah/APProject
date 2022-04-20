package Controller;

import Models.User;

public class Controller {
    public static User LoginButton(String name,String password){
        if(name.length()==0||password.length()==0) return null;
        for (int i = 0; i < name.length(); i++) {
            if(name.charAt(i)-'0'<0||name.charAt(i)-'0'>9)
                return null;
        }
        for (User user :
                User.userList) {
            if(user.id==Integer.parseInt(name)&&user.password.equals(password))
                return user;
        }
        return null;
    }
}
