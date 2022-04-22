package Models;

import resources.ImageResource;
import resources.ResourceManager;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class User {
    public static final HashSet<User> userList = new HashSet<>();

    public String name,email,password,nationalCode,phoneNumber;
    public int id;
    public Image image;
    public Department department;
    public HashSet<Course> courses;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User(String name, String password, String nationalCode, Department department, HashSet<Course> courses) throws NoSuchAlgorithmException {
        userList.add(this);
        this.name = name;
        this.password=hashPassword(password);
        this.nationalCode = nationalCode;
        this.department= department;
        this.courses=courses;
        this.image= ResourceManager.get(ImageResource.defaultProfile);
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
}
