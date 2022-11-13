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

  public void chooseColor(kdTree A, kdTree child){

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

  /*
  public kdTree chooseLeaf(AVL B){
    // On peut couper la feuille? => noeud externe

    // on est sur un noeud interne -> NON
    if((B.leftSon != null)&&(B.rightSon != null)){
      removeNodeGiven(B, B.information); //on supprimer la racine
      chooseLeaf(B.leftSon);
      chooseLeaf(B.rightSon);
    }
    else{

      //on est dans un noeud externe -> OUI
      // max de poids entre les deux fils
      int leafWithTheBiggestWeight = B.maximumOfTwoValues(B.leftSon.getInformation, B.rightSon.getInformation);

      //est ce que on peut couper le noeud?
      // oui
      if (leafWithTheBiggestWeight == B.leftSon.getInformation){
        nodeTemp = new Node();  // garder en memoire le noeud avec le poids le plus faible
        nodeTemp = B.leftSon;

        //supprimer la feuille la plus lourde
        removeNodeGiven(B.leftSon, B.leftSon.getInformation);

        //ajouter ses deux fils
        addNewNode(nodeTemp.leftSon, nodeTemp.leftSon.getInformation);
        addNewNode(nodeTemp.rightSon, nodeTemp.rightSon.getInformation);
      }
      else if (leafWithTheBiggestWeight == B.leftRight.getInformation) {
        nodeTemp = new Node();  // garder en memoire le noeud avec le poids le plus faible
        nodeTemp = B.rightSon;

        //supprimer la feuille la plus lourde
        removeNodeGiven(B.rightSon, B.rightSon.getInformation);

        //ajouter ses deux fils
        addNewNode(nodeTemp.leftSon, nodeTemp.leftSon.getInformation);
        addNewNode(nodeTemp.rightSon, nodeTemp.rightSon.getInformation);

        return nodeTemp;
      }
    }
  }
  */

  private int intConversion(double x){
    Double newX = new Double(x);
    int intX = x.intValue();
    return intX;
  }


}
