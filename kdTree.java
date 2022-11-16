import java.awt.Color;
import java.util.Random;

public class kdTree{

  private boolean axe; //x is true, y is false
  private Color divColor;
  private int width;
  private int height;
  private int division; // coordonée de l'axe de découpe
  private long seed;
  private boolean isRoot;
  private boolean isExtern; //feuille
  private kdTree left;
  private kdTree right;
  private int limXMin,limXSup,limYMin,limYSup; // les limites de division dans les quelles on pourra coupé
  private Pair pointSupLeft;

  public kdTree(int width, int height,long seed){
    this.width = width;
    this.height = height;
    this.seed = seed;
    this.isExtern = true;
  }

  public kdTree(long seed){
    this.seed = seed;
  }


  /**
  en fonction de l'axe de découpe de A, la fonction calcule les dimension d'un enfants de A,
  si l'axe est x et b vrai alors on calcule les dimension de l'enfant gauche qui représente la division en haut de la coupe
  si l'axe est x et b faux alors on calcule les dimension de l'enfant droite qui représente la division en bas de la coupe
  si l'axe est y et b vrai alors on calcule les dimension de l'enfant gauche qui représente la division a gauche de la coupe
  si l'axe est y et b faux alors on calcule les dimension de l'enfant gauche qui représente la division a droite de la coupe
  Ainsi que les limites de la ou il sera possible de coupé dans l'enfant et son point supérieur gauche
  **/
  public void calculDim(boolean b){
    if(this.axe){ //because x = true
      if (b){ // enfant haut
        this.left = new kdTree(this.seed);// pour être sur d'instancier l'enfant au bonne endroit
        // dimension de la division de l'enfant
        this.left.setWidth(this.width);
        this.left.setHeight(this.division - this.pointSupLeft.getY());
        //limites de découpe
        this.left.setLimXDiv(this.pointSupLeft.getX(),this.pointSupLeft.getX()+this.left.getWidth());
        this.left.setLimYDiv(this.pointSupLeft.getY(),this.pointSupLeft.getY()+this.left.getHeight());
        // pointSupLeft de l'enfant
        this.left.getPointSupLeft().setXY(this.pointSupLeft.getX(),this.pointSupLeft.getY());
      } else { //enfant bas
        this.right = new kdTree(this.seed);// pour être sur d'instancier l'enfant au bonne endroit
        // dimension de la division de l'enfant
        this.right.setWidth(this.width);
        this.right.setHeight(this.pointSupLeft.getY()+this.height-this.division);
        //limites de découpe
        this.right.setLimXDiv(this.pointSupLeft.getX(),this.pointSupLeft.getX()+this.left.getWidth());
        this.right.setLimYDiv(this.pointSupLeft.getY(),this.pointSupLeft.getY()+this.left.getHeight());
        // pointSupLeft de l'enfant
        this.right.getPointSupLeft().setXY(this.pointSupLeft.getX(),this.pointSupLeft.getY()+this.right.getHeight());
      }
    } else { //parceque y = false
      if (b) { //enfant gauche
        this.left = new kdTree(this.seed);// pour être sur d'instancier l'enfant au bonne endroit
        // dimension de la division de l'enfant
        this.left.setWidth(this.pointSupLeft.getY()-this.division);
        this.left.setHeight(this.height);
        //limites de découpe
        this.left.setLimXDiv(this.pointSupLeft.getX(),this.pointSupLeft.getX()+this.left.getWidth());
        this.left.setLimYDiv(this.pointSupLeft.getY(),this.pointSupLeft.getY()+this.left.getHeight());
        // pointSupLeft de l'enfant
        this.left.getPointSupLeft().setXY(this.pointSupLeft.getX(),this.pointSupLeft.getY());
      } else {// enfant droite
        this.right = new kdTree(this.seed);// pour être sur d'instancier l'enfant au bonne endroit
        // dimension de la division de l'enfant
        this.right.setWidth(this.width-(this.division-this.pointSupLeft.getX()));
        this.right.setHeight(this.height);
        //limites de découpe
        this.right.setLimXDiv(this.pointSupLeft.getX(),this.pointSupLeft.getX()+this.left.getWidth());
        this.right.setLimYDiv(this.pointSupLeft.getY(),this.pointSupLeft.getY()+this.left.getHeight());
        // pointSupLeft de l'enfant
        this.right.getPointSupLeft().setXY(this.pointSupLeft.getX()+this.right.getWidth(),this.pointSupLeft.getY());
      }
    }
  }

  /**
  la procédure intensi l'enfant gauche ou droit et calcule les données utile à son utilisation
  **/
  public void insertion(boolean b){
    if(b){
      this.calculDim(false);
    } else {
      this.calculDim(true);
    }
  }

  public double w(){
    return this.width*this.height/Math.pow(this.width+this.height,1.5);
  }

  public Color getColor(){
    return this.divColor;
  }

  public boolean  getAxe(){
    return this.axe;
  }

  public kdTree getLeft(){
    return this.left;
  }

  public kdTree getRight(){
    return this.right;
  }

  public int getWidth(){
    return this.width;
  }

  public int getHeight(){
    return this.height;
  }

  public boolean getIsRoot(){
    return this.isRoot;
  }

  public int getLimXMin(){
    return this.limXMin;
  }

  public int getLimXSup(){
    return this.limXSup;
  }

  public int getLimYMin(){
    return this.limYMin;
  }

  public int getLimYSup(){
    return this.limYSup;
  }

  public long getSeed(){
    return this.seed;
  }

  public Pair getPointSupLeft(){
    return this.pointSupLeft;
  }

  public void setAxe(boolean axe){
    this.axe = axe;
  }

  public boolean getIsExtern(){
    return this.isExtern;
  }

  public void setIsExtern(boolean extern){
    this.isExtern = extern;
  }

  public void setWidth(int width){
    this.width = width;
  }

  public void setHeight(int height){
    this.height = height;
  }

  public void setColor(Color colo){
    this.divColor = colo;
  }

  public void setLimXDiv(int xMin,int xSup){
    this.limXMin = xMin;
    this.limXSup = xSup;
  }

  public void setLimYDiv(int yMin,int ySup){
    this.limYMin = yMin;
    this.limYSup = ySup;
  }

  public void SetDivision(int division){
    this.division = division;
  }

}