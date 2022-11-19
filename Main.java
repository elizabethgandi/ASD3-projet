import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main{

  public static void main(String args[]){

    MondriantTree monT = new MondriantTree(100,100,20,10,0.5,10,0.1,7);

    Image canvas = new Image(100,100);

    try{
      monT.toImage(canvas,monT.getTree()).save("mondriant");
    } catch(Exception e){
      System.out.println("probleme");
    }

  }

}
