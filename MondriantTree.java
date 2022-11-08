import java.awt.Color;

public class MondrirantTree{

  private int width,heigth; //width is the width of the painting same for the height
  private int nbleaf;
  private int minDimensionCoupe;
  private float memCouleurProba;
  private float largeurLigne;
  private long seed;
  private List<Color> listColor;
  private kdArbre C; //pointeur de reserve.



  public MondrirantTree(width,height,nbleaf,minDimensionCoupe,memCouleurProba,largeurLigne,seed){
    this.width = width;
    this.height = height;
    this.nbleaf = nbleaf;
    this.minDimensionCoupe = minDimensionCoupe;
    this.memCouleurProba = memCouleurProba;
    this.largeurLigne = largeurLigne;
    this.seed = seed;
    this.listColor = new List<Color>;
    listColor.add(Color.RED);
    listColor.add(Color.BLUE);
    listColor.add(Color.YELLOW);
    listColor.add(Color.WHITE);
    listColor.add(Color.BLACK);
  }


  public kdtree generateRandomTree(kddtree A,AVL B,int nbleaf-1){
    if(nbleaf == 0){
        return A;
    } else {
      if(A.getIsRoot()){
        B.insertion(A,A.w()); //Attention on ne mettra pas qu'un float dans l'avl mais un couple avec aussi un pointeur vers l'element du kdArbre
        A.setLimDiv(A.getWidth(),A.getHeight());
      }
      this.chooseDivision(A);
      A.insertion(false);
      this.chooseColor(A,A.getLeft());
      A.insertion(true);
      this.chooseColor(A,A.getRight());
      B.insertion(A.getLeft(),A.getLeft().w());
      B.insertion(A.getRight(),A.getRight().w());
      this.C = this.chooseLeaf(B);
      if(C.getdimX() > this.minDimensionCoupe && C.getdimY() > this.minDimensionCoupe){
        C.setIsextern(false); // cette ligne sert a indiquer que le neoud n'est pas extene et vas être un neoud de coupe
        return generateRandomTree(C,B,nbleaf-1);
      } else {
        return generateRandomTree(C,B,0);
      }
    }
  }


  public void chooseDivision(kdTree A){
    boolean chaxe;
    Random random = new Random(seed);
    double a = random.double();
    int cut = random.nextInt();

    A.setAxe(a <= this.width/(this.width+this.height));



  }

  //java tout est pointeur
  public pointer chooseLeaf(AVL B){
    //On peut couper la feuille?
      
  }
/*
  public void chooseColor(kdTree A, kdTree child){

    Random random = new Random(seed);
    double a = random.double();
    double b = (1-a)/4;

    this.listColor.remove(A.Color);

    if( a >=0 && a <= this.memCouleurProba){
      child.setColor(A.getColor());
    } else if(a > this.memCouleurProba && a =< this.memCouleurProba+b){
      child.setColor(this.listColor.get(0));
    } else if(a > this.memCouleurProba+b && a =< this.memCouleurProba+2*b){
      child.setColor(this.listColor.get(1));
    } else if(a > this.memCouleurProba+2*b && a =< this.memCouleurProba+3*b){
      child.setColor(this.listColor.get(2));
    } else if((a > this.memCouleurProba+3*b && a =< 1){
      child.setColor(this.listColor.get(3));
    }

    this.listColor.add(A.getColor());
  }

  public kdTree chooseLeaf(AVL B){

  }*/

}
