public class AVL {
    
    private kdTree pointer ; 
    public Node root;
    private int information;
    private int valueOfTheBalance;
    private int valueOfTheHeight;
    private AVL rightSon;
    private AVL leftSon;
    
    
    public AVL(int information , int valueOfTheHeight){
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
    
    public AVL getRightSon() {
        return this.rightSon;
    }
    
    public void setRightSon(AVL rightSon) {
        this.rightSon = rightSon;
    }
    
    public AVL getLeftSon() {
        return this.leftSon;
    }
    public void setLeftSon(AVL leftSon) {
        this.leftSon = leftSon;
    }
    
        /* Method which print AVL */
    public void printAVL(AVL nodeOfAVLTest) {

        if (nodeOfAVLTest != null) {
            printAVL(nodeOfAVLTest.getLeftSon());
            System.out.print(nodeOfAVLTest.getInformation() + " ");
            printAVL(nodeOfAVLTest.getRightSon());
        }
    }
    
    /* Method who return the maximum of two values enter in parameters */
    public int maximumOfTwoValues(int firstValue, int secondValue){

        if(firstValue > secondValue)
            return firstValue;
        
            return secondValue;
    }
    
        /* Method who return the height of a node */
    public int heightValueOfANode(AVL node) {
        if (node == null)
            return 0;
    
        return node.getValueOfTheHeight();
    }
    
    /* Method who make a right rotation */
    public AVL makeARightRotate(AVL nodeRotation){
            //
        AVL subTreeLeft               = nodeRotation.getLeftSon();
        AVL subTreeRightOfSubTreeLeft = subTreeLeft.getRightSon();
    
        subTreeLeft.setRightSon(nodeRotation);
        nodeRotation.setLeftSon(subTreeRightOfSubTreeLeft);
    
        nodeRotation.setValueOfTheHeight(maximumOfTwoValues(heightValueOfANode(nodeRotation.leftSon), heightValueOfANode(nodeRotation.rightSon)) + 1);
        subTreeLeft.setValueOfTheHeight(maximumOfTwoValues(heightValueOfANode(subTreeLeft.leftSon) , heightValueOfANode(subTreeLeft.rightSon))  + 1);
    
        /* subTreeLeft is now the new root */
        return subTreeLeft;
    }
    
    /* Method who make a left rotation */
    public AVL makeALeftRotate(AVL nodeRotation){
        AVL subTreeLeft               = nodeRotation.getRightSon();
        AVL subTreeRightOfSubTreeLeft = subTreeLeft.getLeftSon();
    
        subTreeLeft.setLeftSon(nodeRotation);
        nodeRotation.setRightSon(subTreeRightOfSubTreeLeft);
    
        nodeRotation.setValueOfTheHeight (maximumOfTwoValues(heightValueOfANode(nodeRotation.leftSon), heightValueOfANode(nodeRotation.rightSon)) + 1);
        subTreeLeft.setValueOfTheHeight (maximumOfTwoValues(heightValueOfANode(subTreeLeft.leftSon) , heightValueOfANode(subTreeLeft.rightSon))  + 1);
    
        /* subTreeLeft is now the new root */
        return subTreeLeft;
    }
    
    public AVL sensRotationAVL(int balanceValue, int informationAdd, AVL nodeAdd){
        if (balanceValue > 1){
            if (informationAdd > nodeAdd.getLeftSon().getInformation()){
                /* DROTG */
                nodeAdd.setLeftSon(makeALeftRotate(nodeAdd.getLeftSon()));
                return makeARightRotate(nodeAdd);
            }
            else /*ROTG */
                return makeARightRotate(nodeAdd);
        }
        if (balanceValue < -1){
            if (informationAdd < nodeAdd.getRightSon().getInformation()){
                /* DROTD */
                nodeAdd.setRightSon(makeARightRotate(nodeAdd.getRightSon()));
                return makeALeftRotate(nodeAdd);
            }
            else /*ROTD */
                return makeALeftRotate(nodeAdd);
        }
    
        return nodeAdd;
    }
    
    public AVL insertNode(AVL nodeAdd, int informationAdd){
    
        if (nodeAdd == null) {
            AVL newNode = new AVL(informationAdd,0);
            return newNode;
        }
    
        else if (informationAdd>nodeAdd.getInformation()){
            nodeAdd.setRightSon(addNewNode(nodeAdd.getRightSon(), informationAdd));
        }
    
        else if (informationAdd<=nodeAdd.getInformation()) {
            nodeAdd.setLeftSon(addNewNode(nodeAdd.getLeftSon(), informationAdd));
        }
    
        else {
    
            return nodeAdd;
        }
        return nodeAdd;
    }
    
    public AVL addNewNode(AVL nodeAdd, int informationAdd) {
    
        if (nodeAdd == null)
            return new AVL(informationAdd, 0);
    
        AVL nodeAddResult;
    
        /*Insert the node into the AVL tree with a simple way*/
    
        nodeAdd = insertNode(nodeAdd, informationAdd);
    
        /*Update heights of parent's nodeAdd*/
    
        nodeAdd.setValueOfTheHeight( 1 + maximumOfTwoValues(heightValueOfANode(nodeAdd.getLeftSon()),heightValueOfANode(nodeAdd.getRightSon())));
    
        /*Update balances of nodes in the tree*/
    
        int balanceValue = nodeAdd.getValueOfTheBalance();
    
        /* If the balance = 2 or -2 it's mean that the tree is unbalanced */
    
        nodeAddResult = sensRotationAVL(balanceValue, informationAdd, nodeAdd);
        return nodeAddResult;
    }
    
    public AVL minimumInRightSubTree(AVL root){
        AVL minNodeValue = root;
    
        /* Commme l'AVL est trié on est sur que la plus petite valeur est dans la branche tout à gauche*/
        while(minNodeValue.getLeftSon() != null )
            minNodeValue = minNodeValue.getLeftSon();
    
        return minNodeValue;
    }
    
    public AVL removeNodeGiven (AVL rootNodeRemove, int valueToRemove){
    
        AVL newNode = null;
    
        // way into the tree
        //value < value of the root
        if (valueToRemove < rootNodeRemove.getInformation())
            rootNodeRemove.setLeftSon(removeNodeGiven(rootNodeRemove.getLeftSon(), valueToRemove));
    
            //value > value of the root
        else if (valueToRemove > rootNodeRemove.getInformation())
            rootNodeRemove.setRightSon(removeNodeGiven(rootNodeRemove.getRightSon(), valueToRemove));
    
            // the node deleted is the root
            // the value who has to be deleted is the value of the root of a tree (1), of a leaf (2)
        else {
    
            /*
            if the node has one child (1)
                  4
                /
               1
             */
    
            if ((rootNodeRemove.getLeftSon() == null) || (rootNodeRemove.getRightSon() == null)) {

               if (rootNodeRemove.getLeftSon() == null)
                    newNode = rootNodeRemove.getRightSon();

               if (rootNodeRemove.getRightSon() == null)
                    newNode = rootNodeRemove.getLeftSon();
    
                    /* if the node is a leaf
                            4
                           / \
                          ^   ^
                    */
                if (newNode == null) {
                    newNode = rootNodeRemove;
                    rootNodeRemove = null;
    
                } else
                    rootNodeRemove = newNode;
                }
            else {
                AVL newRoot = minimumInRightSubTree(rootNodeRemove.getRightSon());
    
                rootNodeRemove.setInformation(newRoot.getInformation());
    
                rootNodeRemove.setRightSon(removeNodeGiven(rootNodeRemove.getRightSon(), newRoot.getInformation()));
            }
        }
        return rootNodeRemove;
    }
    
    public AVL removeNode (AVL rootNodeRemove, int valueToRemove){
    
        if (rootNodeRemove == null)
            return null; // because rootNodeRemove is always equals to null
    
           // replace the older root by the new one
        rootNodeRemove = removeNodeGiven(rootNodeRemove, valueToRemove);
    
        if (rootNodeRemove == null)
            return null; // because rootNodeRemove is always equals to null
    
        // modifie the height of the root in take the maximum of the height of his two sons
        rootNodeRemove.setValueOfTheHeight(maximumOfTwoValues(rootNodeRemove.getLeftSon().getValueOfTheHeight(), rootNodeRemove.getRightSon().getValueOfTheHeight()) + 1);
    
        //gérer la balance
        int balanceValue = rootNodeRemove.getValueOfTheBalance();
    
        rootNodeRemove = sensRotationAVL(balanceValue, valueToRemove, rootNodeRemove);
    
        return rootNodeRemove;
    }

    public kdTree getPointer() {
        return this.pointer;
    }

    public void setPointer(kdTree newPointer) {
        this.pointer = newPointer;
    }
}
