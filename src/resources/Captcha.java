package resources;


import java.awt.*;
import java.util.HashMap;

public class Captcha {
    public static HashMap<Integer,Captcha> getCaptcha = new HashMap<>();
    public Image image;
    public String string;
    public Captcha(int i){
        getCaptcha.put(i,this);
        switch (i){
            case 0:
                image=ResourceManager.get(ImageResource.captcha0);
                string="5364";
                break;
            case 1:
                image=ResourceManager.get(ImageResource.captcha1);
                string="2976";
                break;
            case 2:
                image=ResourceManager.get(ImageResource.captcha2);
                string="7143";
                break;
            case 3:
                image=ResourceManager.get(ImageResource.captcha3);
                string="2769";
                break;
            case 4:
                image=ResourceManager.get(ImageResource.captcha4);
                string="2440";
                break;
            case 5:
                image=ResourceManager.get(ImageResource.captcha5);
                string="4497";
                break;
        }
    }
}
