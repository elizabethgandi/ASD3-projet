public class Pair{

  Pair dim;
  boolean selec;
  int x;
  int y;


  public Pair(Pair dim,boolean selec){
    this.dim = dim;
    this.selec = selec;
  }

  public Pair(int x,int y){
    this.x = x;
    this.y = y;
  }


  public Pair getDim(){
    return this.dim;
  }

  public boolean getSelec(){
    return this.selec;
  }

  public int getX(){
    return this.y;
  }

  public int getY(){
    return this.y;
  }

  public void setX(int x){
    this.x = x;
  }

  public void SetY(int y){
    this.y = y;
  }

  public void setXY(int x,int y){
    this.x = x;
    this.y = y;
  }

}
