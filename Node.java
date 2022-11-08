public class Node {
    private int information;
    private int valueOfTheBalance;
    public int valueOfTheHeight;
    public Node rightSon;
    public Node leftSon;

    public Node(int information , int valueOfTheHeight){
        this.information       = information;
        this.valueOfTheHeight  = valueOfTheHeight;
    }

    public int getInformation() {
        return information;
    }

    public void setInformation(int information) {
        this.information = information;
    }

    public int getValueOfTheBalance() {
        return valueOfTheBalance;
    }

    public void setValueOfTheBalance(int valueOfTheBalance) {
        this.valueOfTheBalance = valueOfTheBalance;
    }

    public int getValueOfTheHeight() {
        return valueOfTheHeight;
    }

    public void setValueOfTheHeight(int valueOfTheHeight) {
        this.valueOfTheHeight = valueOfTheHeight;
    }

    public Node getRightSon() {
        return rightSon;
    }

    public void setRightSon(Node rightSon) {
        this.rightSon = rightSon;
    }

    public Node getLeftSon() {
        return leftSon;
    }
    public void setLeftSon(Node leftSon) {
        this.leftSon = leftSon;
    }
}
