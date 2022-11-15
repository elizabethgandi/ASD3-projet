public class AVL {
    public Node root;
    private kdTree pointer ; 

    /* Method which print AVL */
    public void printAVL(Node nodeOfAVLTest) {

        if (nodeOfAVLTest != null) {
            System.out.print(nodeOfAVLTest.getInformation() + " ");
            printAVL(nodeOfAVLTest.leftSon);
            printAVL(nodeOfAVLTest.rightSon);
        }

    }

    /* Method who return the maximum of two values enter in parameters */
    public int maximumOfTwoValues(int firstValue, int secondValue){

        if(firstValue > secondValue)
            return firstValue;

        return secondValue;
    }

    /* Method who return the height of a node */
    public int heightValueOfANode(Node node) {
        if (node == null)
            return 0;

        return node.getValueOfTheHeight();
    }

    /* Method who make a right rotation */
    public Node makeARightRotate(Node nodeRotation){
        //
        Node subTreeLeft               = nodeRotation.leftSon;
        Node subTreeRightOfSubTreeLeft = subTreeLeft.rightSon;

        subTreeLeft.rightSon = nodeRotation;
        nodeRotation.leftSon = subTreeRightOfSubTreeLeft;

        nodeRotation.valueOfTheHeight = maximumOfTwoValues(heightValueOfANode(nodeRotation.leftSon), heightValueOfANode(nodeRotation.rightSon)) + 1;
        subTreeLeft.valueOfTheHeight  = maximumOfTwoValues(heightValueOfANode(subTreeLeft.leftSon) , heightValueOfANode(subTreeLeft.rightSon))  + 1;

        /* subTreeLeft is now the new root */
        return subTreeLeft;
    }

    /* Method who make a left rotation */
    public Node makeALeftRotate(Node nodeRotation){
        Node subTreeLeft               = nodeRotation.rightSon;
        Node subTreeRightOfSubTreeLeft = subTreeLeft.leftSon;

        subTreeLeft.leftSon   = nodeRotation;
        nodeRotation.rightSon = subTreeRightOfSubTreeLeft;

        nodeRotation.valueOfTheHeight = maximumOfTwoValues(heightValueOfANode(nodeRotation.leftSon), heightValueOfANode(nodeRotation.rightSon)) + 1;
        subTreeLeft.valueOfTheHeight  = maximumOfTwoValues(heightValueOfANode(subTreeLeft.leftSon) , heightValueOfANode(subTreeLeft.rightSon))  + 1;

        /* subTreeLeft is now the new root */
        return subTreeLeft;
    }

    public Node sensRotationAVL(int balanceValue, int informationAdd, Node nodeAdd){
        if (balanceValue > 1){
            if (informationAdd > nodeAdd.leftSon.getInformation()){
                /* DROTG */
                nodeAdd.leftSon = makeALeftRotate(nodeAdd.leftSon);
                return makeARightRotate(nodeAdd);
            }
            else /*ROTG */
                return makeARightRotate(nodeAdd);
        }
        if (balanceValue < -1){
            if (informationAdd < nodeAdd.rightSon.getInformation()){
                /* DROTD */
                nodeAdd.rightSon = makeARightRotate(nodeAdd.rightSon);
                return makeALeftRotate(nodeAdd);
            }
            else /*ROTD */
                return makeALeftRotate(nodeAdd);
        }

        return nodeAdd;
    }

    public Node insertNode(Node nodeAdd, int informationAdd){

        if (nodeAdd == null) {
            Node newNode = new Node(informationAdd,0);
            return newNode;
        }

        else if (informationAdd>nodeAdd.getInformation()){
            nodeAdd.rightSon = addNewNode(nodeAdd.getRightSon(), informationAdd);
        }

        else if (informationAdd<=nodeAdd.getInformation()) {
            nodeAdd.leftSon = addNewNode(nodeAdd.getLeftSon(), informationAdd);
        }

        else {

            return nodeAdd;
        }
        return nodeAdd;
    }

    public Node addNewNode(kdTree nodeAdd, double informationAdd) {

        if (nodeAdd == null)
            return new Node(informationAdd, 0);

        Node nodeAddResult;

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

    public Node minimumInRightSubTree(Node root){
        Node minNodeValue = root;

        /* Commme l'AVL est trié on est sur que la plus petite valeure est dans la branche tout à gauche*/
        while(minNodeValue.leftSon != null )
            minNodeValue = minNodeValue.leftSon;

        return minNodeValue;
    }

    public Node max(){
        return maximumInRightSubTree(this);
    }

    public Node maximumInRightSubTree(Node root){
        Node maxNodeValue = root;

        /* Commme l'AVL est trié on est sur que la plus grande valeure est dans la branche tout à droite*/
        while(maxNodeValue.rightSon != null )
            maxNodeValue = maxNodeValue.rightSon;

        return maxNodeValue;
    }


    public Node removeNodeGiven (Node rootNodeRemove, int valueToRemove){

        Node newNode = null;

        // way into the tree
        //value < value of the root
        if (valueToRemove < rootNodeRemove.getInformation())
            rootNodeRemove.leftSon = removeNodeGiven(rootNodeRemove.leftSon, valueToRemove);

        //value > value of the root
        else if (valueToRemove > rootNodeRemove.getInformation())
            rootNodeRemove.rightSon = removeNodeGiven(rootNodeRemove.rightSon, valueToRemove);

        // the node deleted is the root
        // the value who has to be deleted is the value of the root of a tree (1), of a leaf (2)
        else {

            /*
             if the node has one child (1)
                  4
                /
               1
             */

            if ((rootNodeRemove.leftSon == null) || (rootNodeRemove.rightSon == null)) {

                if (rootNodeRemove.leftSon == null)
                    newNode = rootNodeRemove.rightSon;

                if (rootNodeRemove.rightSon == null)
                    newNode = rootNodeRemove.leftSon;

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
                    Node newRoot = minimumInRightSubTree(rootNodeRemove.rightSon);

                rootNodeRemove.setInformation(newRoot.getInformation());

                rootNodeRemove.rightSon = removeNodeGiven(rootNodeRemove.rightSon, newRoot.getInformation());
            }
        }
        return rootNodeRemove;
    }

    public Node removeNode (Node rootNodeRemove, int valueToRemove){

        if (rootNodeRemove == null)
            return null; // because rootNodeRemove is always equals to null

        // replace the older root by the new one
        rootNodeRemove = removeNodeGiven(rootNodeRemove, valueToRemove);

        if (rootNodeRemove == null)
            return null; // because rootNodeRemove is always equals to null

        // modifie the height of the root in take the maximum of the height of his two sons
        rootNodeRemove.setValueOfTheHeight(maximumOfTwoValues(rootNodeRemove.leftSon.getValueOfTheHeight(), rootNodeRemove.rightSon.getValueOfTheHeight()) + 1);

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
