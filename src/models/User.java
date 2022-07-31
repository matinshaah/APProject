package models;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class  User {
    public static final ArrayList<User> userList = new ArrayList<>();

    public String name,email="-",password,nationalCode,phoneNumber;
    public int id;
    public String image;
    public Department department;
    public ArrayList<Course> courses;
    public LocalDateTime lastLogin;
    public Color color=Color.pink;

    public void setEmail(String email) {
        this.email = email;
    }


    public void setDepartment(Department department) {
        this.department = department;
    }

    public User(String name, String password, String nationalCode, Department department, ArrayList<Course> courses) throws NoSuchAlgorithmException {
        userList.add(this);
        this.name = name;
        this.password=hashPassword(password);
        this.nationalCode = nationalCode;
        this.department= department;
//        this.courses=courses!=null?courses:new ArrayList<>();
        this.courses=new ArrayList<>();
        this.image= "src/resources/Images/defaultProfile.png";
    }
    public static String hashPassword (String password) throws NoSuchAlgorithmException {
        MessageDigest digest =MessageDigest.getInstance("SHA-256");
        byte[] encodeHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodeHash);
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static User getUserByName(String name){
        for (User u :
                userList) {
            if(u.name.equals(name))
                return u;
        }
        return null;
    }
}
