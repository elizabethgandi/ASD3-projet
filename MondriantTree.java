import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

public class MondriantTree{

  private int width,height; //width is the width of the painting same for the height
  private int nbleaf;
  private int minDimensionCoupe;
  private double memCouleurProba;
  private double largeurLigne;
  private double proportionCoupe;
  private long seed;
  private ArrayList<Color> listColor;
  private kdTree C; //pointeur sur la feuille sur laquelle on vas faire des opérations



  public MondriantTree(int width, int height,int nbleaf,int minDimensionCoupe,double memCouleurProba,double largeurLigne,double proportionCoupe,long seed){
    this.width = width;
    this.height = height;
    this.nbleaf = nbleaf;
    this.minDimensionCoupe = minDimensionCoupe;
    this.memCouleurProba = memCouleurProba;
    this.largeurLigne = largeurLigne;
    this.proportionCoupe = proportionCoupe;
    this.seed = seed;
    this.listColor = new ArrayList<Color>();
    boolean verif;
    verif = listColor.add(Color.RED);
    verif = listColor.add(Color.BLUE);
    verif = listColor.add(Color.YELLOW);
    verif = listColor.add(Color.WHITE);
    verif = listColor.add(Color.BLACK);
  }


  public kdTree generateRandomTree(kdTree A,AVL B,int nbleaf){
    if(nbleaf == 0){
        return A;
    } else {
      if(A.getIsRoot()){// plus de vérification car là on admet que les opérations vont biense faire
        nbleaf--;
        B.insertion(A,A.w()); //Attention on ne mettra pas qu'un float dans l'avl mais un couple avec aussi un pointeur vers l'element du kdArbre
        A.setLimXDiv(this.intConversion(A.getWidth()*this.proportionCoupe),A.getWidth());
        A.setLimYDiv(this.intConversion(A.getHeight()*this.proportionCoupe),A.getHeight());

      }
      this.chooseDivision(A);
      A.insertion(false);
      this.chooseColor(A,A.getLeft());
      A.insertion(true);
      this.chooseColor(A,A.getRight());
      //test si on peut diviser les feuilles? => optimisation passer de nlogn à logn dans le pire cas
      B.insertion(A.getLeft(),A.getLeft().w());
      B.insertion(A.getRight(),A.getRight().w());
      this.C = this.chooseLeaf(B);// B un AVL faire une condition pour verifier que ce que l'on va decouper est bien une feuille
      if(C.getWidth() > this.minDimensionCoupe && C.getHeight() > this.minDimensionCoupe){
        C.setIsextern(false); // cette ligne sert a indiquer que le neoud n'est pas extene et vas être un neoud de coupe
        return generateRandomTree(C,B,nbleaf-1);
      } else {
        return generateRandomTree(C,B,0);
      }
    }
  }


  public void chooseDivision(kdTree A){
    boolean chaxe; // chaxe pour choose axe
    Random random = new Random(seed);
    double a = random.nextDouble();

    A.setAxe(a <= this.width/(this.width+this.height));

    if(A.getAxe()){
      if(A.getPointSupLeft().getY()-this.height == A.getHeight()){ // alors la division est frontaliere et en bas
        int lim = A.getPointSupLeft().getX()+A.getWidth(); //pour la clartédu code
        A.setLimXDiv(A.getPointSupLeft().getX(),lim-(int)this.proportionCoupe*lim);// pour ne pas couper dans une zone interdite
        A.SetDivision(A.getLimXMin() + random.nextInt(A.getLimXSup()-A.getLimXMin()));
      }else if(A.getHeight() == this.height - (A.getPointSupLeft().getY()+A.getHeight())){// alors la division est frontaliere et en haut
        int lim = A.getPointSupLeft().getX();//pour la clartédu code
        A.setLimXDiv(lim+(int)lim*this.proportionCoupe,A.getPointSupLeft().getX()+A.getWidth());// pour ne pas couper dans une zone interdite
        A.SetDivision(A.getLimXMin() + random.nextInt(A.getLimXSup()-A.getLimXMin()));
      } else {
        A.SetDivision(A.getLimXMin() + random.nextInt(A.getLimXSup()-A.getLimXMin()));
      }
    } else {
      if(A.getPointSupLeft().getX()-this.width == A.getWidth()){ // alors la division est frontaliere et a droite
        int lim = A.getPointSupLeft().getY()+A.getHeight();//pour la clartédu code
        A.setLimYDiv(A.getPointSupLeft().getY(),lim-(int)lim*this.proportionCoupe);// pour ne pas couper dans une zone interdite
        A.SetDivision(A.getLimYMin() + random.nextInt(A.getLimYSup()-A.getLimYMin()));
      } else if(A.getWidth() == this.width - (A.getPointSupLeft().getX()+A.getWidth())){// alors la division est frontaliere et a droite
        int lim = A.getPointSupLeft().getY();//pour la clartédu code
        A.setLimYDiv(lim+lim*this.proportionCoupe,A.getPointSupLeft().getY()+A.getHeight());// pour ne pas couper dans une zone interdite
      A.SetDivision(A.getLimYMin() + random.nextInt(A.getLimYSup()-A.getLimYMin()));
      } else {
        A.SetDivision(A.getLimYMin() + random.nextInt(A.getLimYSup()-A.getLimYMin()));
      }
    }
  }

  public void chooseColor(kdTree child){

    Random random = new Random(seed);
    double a = random.nextDouble();
    double b = (1-a)/4;

    this.listColor.remove(A.getColor());

    if( a >=0 && a <= this.memCouleurProba){
      child.setColor(A.getColor());
    } else if(a > this.memCouleurProba && a <= this.memCouleurProba+b){
      child.setColor(this.listColor.get(0));
    } else if(a > this.memCouleurProba+b && a <= this.memCouleurProba+2*b){
      child.setColor(this.listColor.get(1));
    } else if(a > this.memCouleurProba+2*b && a <= this.memCouleurProba+3*b){
      child.setColor(this.listColor.get(2));
    } else if(a > this.memCouleurProba+3*b && a <= 1){
      child.setColor(this.listColor.get(3));
    }

    this.listColor.add(A.getColor());
  }

  public BufferedImage toImage(MondrirantTree mTree){

    //obtenir les coordonées X,Y du point superieur gauche pour pouvoir faire nos calculs
    int pointSupLeftX = mTree.getX();
    int pointSupLeftY = mTree.getY();

    Color col = chooseColor(mTree);

    //éviter de faire des rappels à chaque fois
    int addWidthLine = 1/2*largeurLigne;

    // 1. Si le noeud est une feuille => on coupe
    // ligne grise c'est largeurLigne widthLine?

    // 1.2. est frontalier (else)
    // 1.2.1 côté gauche (if)
    if(A.getWidth() == this.width - (A.getPointSupLeft().getX()+A.getWidth())){
      int x1 = pointSupLeftX;                
      int x2 = (pointSupLeftX + mTree.width) - addWidthLine;   
      int x3 = pointSupLeftY;                  
      int x4 = pointSupLeftY  + mTree.heigth + addWidthLine;   
      
      mTree.setRectangle(x1, x2, x3, x4, col);
    }
    // 1.2.2 côté dessus (else if)
    else if(A.getHeight() == this.height - (A.getPointSupLeft().getY()+A.getHeight())){
      int x1 = pointSupLeftX ;                  
      int x2 = (pointSupLeftX + mTree.width) ;   
      int x3 = pointSupLeftY  + addWidthLine;                  
      int x4 = pointSupLeftY  + mTree.heigth + addWidthLine; 

      mTree.setRectangle(x1, x2, x3, x4, col);
    }
    // 1.2.3 côté droit (else if)
    else if(A.getPointSupLeft().getX()-this.width == A.getWidth()){ 
      int x1 = pointSupLeftX  + addWidthLine;                  
      int x2 = (pointSupLeftX + mTree.width);   
      int x3 = pointSupLeftY  + addWidthLine;                  
      int x4 = pointSupLeftY  + mTree.heigth;   

      mTree.setRectangle(x1, x2, x3, x4, col);
    }
    // 1.2.4 côté dessous (else)
    if(A.getPointSupLeft().getY()-this.height == A.getHeight()){ 
      int x1 = pointSupLeftX  + addWidthLine;                  
      int x2 = (pointSupLeftX + mTree.width) - addWidthLine;   
      int x3 = pointSupLeftY  ;                  
      int x4 = pointSupLeftY  + mTree.heigth ;   

      mTree.setRectangle(x1, x2, x3, x4, col);
    }
    // 1.1. n'est pas frontalier (if)
    else{
      int x1 = pointSupLeftX  + addWidthLine;                  //startX
      int x2 = (pointSupLeftX + mTree.width) - addWidthLine;   //endX A VERIFIER mTree.width
      int x3 = pointSupLeftY  + addWidthLine;                  //startY
      int x4 = pointSupLeftY  + mTree.heigth + addWidthLine;   //endY
    
      mTree.setRectangle(x1, x2, x3, x4, col);
    }
    // 2. Si le noeud est interne => on se déplace dans l'arbre

    // 2.1 côté gauche
    toImage(mTree.fg);

    //2.2 côté droit
    toImage(mTree.fd);

  }

 
  public kdTree chooseLeaf(AVL B){
    // On peut couper la feuille? => noeud externe

    if(!B.getIsExtern()) //dans le cas le cas ou il est egal à true
      return chooseLeaf(B.removeNodeGiven(B, B.getInformation()));
    
    else{

      //on est dans un noeud externe -> OUI
      AVL leafWithTheBiggestWeight = B.max(); //getRightSon()

      //est ce que on peut couper le noeud?
      // oui
      if(leafWithTheBiggestWeight.getPointer().getWidth() > this.minDimensionCoupe && leafWithTheBiggestWeight.getPointer().getHeight() > this.minDimensionCoupe){
        // garder en memoire le noeud avec le poids le plus fort

        //supprimer la feuille la plus lourde
        leafWithTheBiggestWeight.removeNodeGiven(leafWithTheBiggestWeight, leafWithTheBiggestWeight.getInformation());

        //ajouter ses deux fils
        /*addNewNode(nodeTemp.leftSon, nodeTemp.leftSon.getInformation);
          addNewNode(nodeTemp.rightSon, nodeTemp.rightSon.getInformation);*/
      
        return leafWithTheBiggestWeight;

      } else
          return null;
    } 
  }
}
