/* Anthony Marrongelli
 * COP3503
 * 7/1/2023
 */


public class TwoFourTree {
    private class TwoFourTreeItem {
        int values = 1;
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        public boolean isTwoNode() {
            if(values == 1) return true;
            return false;
        }

        public boolean isThreeNode() {
            if(values == 2) return true;
            return false;
        }

        public boolean isFourNode() {
            if(values == 3) return true;
            return false;
        }

         public boolean isRoot() {
            if(this == root) return true;
            return false;
        }

        //Creating a two node -- Assings value to that variable
        public TwoFourTreeItem(int value1) {
            this.values = 1;
            this.value1 = value1;
            this.isLeaf = true;
        }

        //Creating a three node -- Assigns the values to 2 as there are two values and gives the values to variables
        public TwoFourTreeItem(int value1, int value2) {
            this.values = 2;
            this.isLeaf = true;

            //Putting them in increasing order
            if(value1 > value2) {
                this.value1 = value2;
                this.value2 = value1;
            } 
            else {
                this.value1 = value1;
                this.value2 = value2;
            }
        }
        

        //Creating a four node -- Assigns values to 3 and gives literal values to variables
        public TwoFourTreeItem(int value1, int value2, int value3) {
            this.values = 3;
            this.isLeaf = true;

            //Putting them in increasing order
            //If first value is greatest, then compares the others
            if(value1 >= value2 && value1 >= value3) {
                this.value3 = value1;
                if(value2 >= value3) {
                    this.value2 = value2;
                    this.value1 = value3;
                }
                else {
                    this.value2 = value3;
                    this.value1 = value2;
                }
            }

            //If second value is greatest, then compares the others
            if(value2 >= value1 && value2 >= value3) {
                this.value3 = value2;
                if(value1 >= value3) {
                    this.value2 = value1;
                    this.value1 = value3;
                }
                else {
                    this.value2 = value3;
                    this.value1 = value1;
                }
            }

            //If third value is greatest then compares the others
            if(value3 >= value1 && value3 >= value2) {
                this.value3 = value3;
                if(value1 >= value2) {
                    this.value2 = value1;
                    this.value1 = value2;
                }
                else {
                    this.value2 = value2;
                    this.value1 = value1;
                }
            }
        }

        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) {
            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1);
        }
    }

    TwoFourTreeItem root = null;



    /* Takes an integer as input, adds the value to the tree, returns true or false based on success of function */
    public boolean addValue(int value) {
        
        /* Checking if the tree has value, if it does we dont need to add it */
        if(hasValue(value)) return true;
        
        /* Case that the Tree is Empty */
        if(root == null) {
            root = new TwoFourTreeItem(value);
            return true;
        }

        /* If root is Leaf */
        if(root.isLeaf) {
            /* Checking what type of node it is -- and performing appropriate change */
            /* If its a two node add value and re-order */
            if(root.isTwoNode()) {
                root.values = 2;
                if(root.value1 > value) {
                    root.value2 = root.value1;
                    root.value1 = value;
                } else {
                    root.value2 = value;
                }
                return true;
            }
            /* If its a three node add value and re-order */
            else if(root.isThreeNode()) {
                root.values = 3;

                if(root.value1 > value) {
                    root.value3 = root.value2;
                    root.value2 = root.value1;
                    root.value1 = value;
                } else if(root.value2 > value) {
                    root.value3 = root.value2;
                    root.value2 = value;
                } else {
                    root.value3 = value;
                }
                return true;
            }
            /* If its a four node, we need to split the node and then insert */
            else if(root.isFourNode()) {
                /* Seperating nodes and re-establishing root */
                TwoFourTreeItem newRoot = new TwoFourTreeItem(root.value2);
                newRoot.isLeaf = false;
                newRoot.leftChild = new TwoFourTreeItem(root.value1);
                newRoot.leftChild.parent = newRoot;
                newRoot.rightChild = new TwoFourTreeItem(root.value3);
                newRoot.rightChild.parent = newRoot;
                root = newRoot;
            }
        }

        /* While loop to insert  ---- ENDS WHEN ARRIVING AT THE LEAF NODE WE ARE LOOKING FOR*/
            TwoFourTreeItem node = root;
            while(!node.isLeaf) {

                if(node.isTwoNode()) {
                    /* Cases:
                        Node with both children -- Traverse Left/Right depending on the number
                    */
                    if(node.value1 > value) {               
                        node = node.leftChild;
                        continue;
                    } else {
                        node = node.rightChild;
                        continue;
                    }
                }                                                                                           
                else if(node.isThreeNode()) {
                    /* Cases:
                        Node will have three children -- Traverse Left/Center/Right depending on number
                    */
                    if(node.value1 > value) {
                        node = node.leftChild;
                        continue;
                    } else if(node.value2 > value) {
                        node = node.centerChild;
                        continue;
                    } else {
                        node = node.rightChild;
                        continue;
                    }
                }
                else if(node.isFourNode()) {                   
                    /* Splitting a four node when we encounter one on the way down */
                    int liftValue = node.value2;
                    if(node.isRoot()) {
                        TwoFourTreeItem newRoot = new TwoFourTreeItem(node.value2);
                        newRoot.isLeaf = false;
                        newRoot.leftChild = new TwoFourTreeItem(node.value1);
                        newRoot.leftChild.isLeaf = false;
                        newRoot.leftChild.parent = newRoot;
                        newRoot.rightChild = new TwoFourTreeItem(node.value3);
                        newRoot.rightChild.isLeaf = false;
                        newRoot.rightChild.parent = newRoot;

                        newRoot.leftChild.leftChild = node.leftChild;
                        newRoot.leftChild.leftChild.parent = newRoot.leftChild;
                        newRoot.leftChild.rightChild = node.centerLeftChild;
                        newRoot.leftChild.rightChild.parent = newRoot.leftChild;
                        newRoot.rightChild.leftChild = node.centerRightChild;
                        newRoot.rightChild.leftChild.parent = newRoot.rightChild;
                        newRoot.rightChild.rightChild = node.rightChild;
                        newRoot.rightChild.rightChild.parent = newRoot.rightChild;


                        root = newRoot;
                        node = root;
                        continue;
                    }
                    else if(node == node.parent.leftChild) {
                        /* Checking what type of node the parent is so we can move values properly and re-assign correct pointers */
                        if(node.parent.isTwoNode()) {
                            node.parent.values = 2;
                            node.parent.value2 = node.parent.value1;
                            node.parent.value1 = liftValue;

                            node.parent.leftChild = new TwoFourTreeItem(node.value1);
                            node.parent.leftChild.isLeaf = false;
                            node.parent.leftChild.parent = node.parent;
                            node.parent.leftChild.leftChild = node.leftChild;
                            node.parent.leftChild.rightChild = node.centerLeftChild;
                            node.parent.leftChild.leftChild.parent = node.parent.leftChild;
                            node.parent.leftChild.rightChild.parent = node.parent.leftChild;

                            node.parent.centerChild = new TwoFourTreeItem(node.value3);
                            node.parent.centerChild.isLeaf = false;
                            node.parent.centerChild.parent = node.parent;
                            node.parent.centerChild.leftChild = node.centerRightChild;
                            node.parent.centerChild.rightChild = node.rightChild;
                            node.parent.centerChild.leftChild.parent = node.parent.centerChild;
                            node.parent.centerChild.rightChild.parent = node.parent.centerChild;

                            node = node.parent;
                            continue;
                        } else {
                            node.parent.values = 3;
                            node.parent.value3 = node.parent.value2;
                            node.parent.value2 = node.parent.value1;
                            node.parent.value1 = liftValue;

                            node.parent.leftChild = new TwoFourTreeItem(node.value1);
                            node.parent.leftChild.isLeaf = false;
                            node.parent.leftChild.parent = node.parent;
                            node.parent.leftChild.leftChild = node.leftChild;
                            node.parent.leftChild.rightChild = node.centerLeftChild;
                            node.parent.leftChild.leftChild.parent = node.parent.leftChild;
                            node.parent.leftChild.rightChild.parent = node.parent.rightChild;

                            node.parent.centerLeftChild = new TwoFourTreeItem(node.value3);
                            node.parent.centerLeftChild.isLeaf = false;
                            node.parent.centerLeftChild.parent = node.parent;
                            node.parent.centerLeftChild.leftChild = node.centerRightChild;
                            node.parent.centerLeftChild.rightChild = node.rightChild;
                            node.parent.centerLeftChild.leftChild.parent = node.parent.centerLeftChild;
                            node.parent.centerLeftChild.rightChild.parent = node.parent.centerLeftChild;

                            node.parent.centerRightChild = node.parent.centerChild;
                            node.parent.centerChild = null;

                            node = node.parent;
                            continue;
                        }
                    } else if(node == node.parent.centerChild) {
                        /* Only one possibility here -- the parent has to be a Three Node -- bringing value up to middle  */
                        node.parent.values = 3;
                        node.parent.value3 = node.parent.value2;
                        node.parent.value2 = liftValue;

                        node.parent.centerLeftChild = new TwoFourTreeItem(node.value1);
                        node.parent.centerLeftChild.isLeaf = false;
                        node.parent.centerLeftChild.parent = node.parent;
                        node.parent.centerLeftChild.leftChild = node.leftChild;
                        node.parent.centerLeftChild.rightChild = node.centerRightChild;
                        node.parent.centerLeftChild.leftChild.parent = node.parent.centerLeftChild;
                        node.parent.centerLeftChild.rightChild.parent = node.parent.centerLeftChild;

                        node.parent.centerRightChild = new TwoFourTreeItem(node.value3);
                        node.parent.centerRightChild.isLeaf = false;
                        node.parent.centerRightChild.parent = node.parent;
                        node.parent.centerRightChild.leftChild = node.centerRightChild;
                        node.parent.centerRightChild.rightChild = node.rightChild;
                        node.parent.centerRightChild.leftChild.parent = node.parent.centerRightChild;
                        node.parent.centerRightChild.rightChild.parent = node.parent.centerRightChild;

                        node.parent.centerChild = null;

                        node = node.parent;
                        continue;

                    } else {
    
                        /* Is right child, could be two/three node */
                        if(node.parent.isTwoNode()) {
                            node.parent.values = 2;
                            node.parent.value2 = liftValue;

                            node.parent.centerChild = new TwoFourTreeItem(node.value1);
                            node.parent.centerChild.isLeaf = false;
                            node.parent.centerChild.parent = node.parent;
                            node.parent.centerChild.leftChild = node.leftChild;
                            node.parent.centerChild.leftChild.parent = node.parent.centerChild;
                            node.parent.centerChild.rightChild = node.centerLeftChild;
                            node.parent.centerChild.rightChild.parent = node.centerChild;

                            node.parent.rightChild = new TwoFourTreeItem(node.value3);
                            node.parent.rightChild.isLeaf = false;
                            node.parent.rightChild.parent = node.parent;
                            node.parent.rightChild.leftChild = node.centerRightChild;
                            node.parent.rightChild.leftChild.parent = node.parent.rightChild;
                            node.parent.rightChild.rightChild = node.rightChild;
                            node.parent.rightChild.rightChild.parent = node.parent.rightChild;

                            node = node.parent;
                            continue;
                        } else {
                            //IS THREE NODE
                            node.parent.values = 3;
                            node.parent.value3 = liftValue;

                            node.parent.centerLeftChild = node.parent.centerChild;
                            node.parent.centerChild = null;

                            node.parent.centerRightChild = new TwoFourTreeItem(node.value1);
                            node.parent.centerRightChild.isLeaf = false;
                            node.parent.centerRightChild.leftChild = node.leftChild;
                            node.parent.centerRightChild.leftChild.parent = node.parent.centerRightChild;
                            node.parent.centerRightChild.rightChild = node.centerLeftChild;
                            node.parent.centerRightChild.rightChild.parent = node.parent.centerRightChild;
                            node.parent.centerRightChild.parent = node.parent;

                            node.parent.rightChild = new TwoFourTreeItem(node.value3);
                            node.parent.rightChild.isLeaf = false;
                            node.parent.rightChild.leftChild = node.centerRightChild;
                            node.parent.rightChild.rightChild = node.rightChild;
                            node.parent.rightChild.leftChild.parent = node.parent.rightChild;
                            node.parent.rightChild.rightChild.parent = node.parent.rightChild;
                            node.parent.rightChild.parent = node.parent;
                            
                            node = node.parent;
                            continue;
                        }
                    }
                }
            }

            /* Current Node will be Leaf node for insertion (CAN BE 2 3 or 4 NODE)*/
            if(node.isTwoNode()) {
                node.values = 2;

                if(value > node.value1) {
                    node.value2 = value;
                } else {
                    node.value2 = node.value1;
                    node.value1 = value;
                }
                return true;
            }
            else if(node.isThreeNode()) {
                node.values = 3;

                if(value > node.value2) {
                    node.value3 = value;
                } else if(value > node.value1) {
                    node.value3 = node.value2;
                    node.value2 = value;
                } else {
                    node.value3 = node.value2;
                    node.value2 = node.value1;
                    node.value1 = value;
                }
                return true;
            }
            else {
                // IS A FOUR NODE
                int liftValue = node.value2;
                    if(node == node.parent.leftChild) {
                        /* Checking what type of node the parent is so we can move values properly and re-assign correct pointers */
                        if(node.parent.isTwoNode()) {
                            node.parent.values = 2;
                            node.parent.value2 = node.parent.value1;
                            node.parent.value1 = liftValue;

                            node.parent.leftChild = new TwoFourTreeItem(node.value1);
                            node.parent.leftChild.parent = node.parent;
                            node.parent.centerChild = new TwoFourTreeItem(node.value3);
                            node.parent.centerChild.parent = node.parent;

                            node = node.parent;
                            if(node.value1 > value) {
                                node = node.leftChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }

                            } else {
                                node = node.centerChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }
                            }

                            return true;

                        } else {
                            node.parent.values = 3;
                            node.parent.value3 = node.parent.value2;
                            node.parent.value2 = node.parent.value1;
                            node.parent.value1 = liftValue;

                            node.parent.leftChild = new TwoFourTreeItem(node.value1);
                            node.parent.leftChild.parent = node.parent;
                            node.parent.centerLeftChild = new TwoFourTreeItem(node.value3);
                            node.parent.centerLeftChild.parent = node.parent;
                            node.parent.centerRightChild = node.parent.centerChild;
                            node.parent.centerRightChild.parent = node.parent;
                            node.parent.centerChild = null;

                            node = node.parent;
                            //POSSIBLE ERRORS HERE
                            if(node.value1 > value) {
                                node = node.leftChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }

                            } else {
                                node = node.centerLeftChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }
                            }
                            return true;
                            // ENDS HERE

                        }
                    } else if(node == node.parent.centerChild) {
                        /* Only one possibility here -- the parent has to be a Three Node -- bringing value up to middle  */
                        node.parent.values = 3;
                        node.parent.value3 = node.parent.value2;
                        node.parent.value2 = liftValue;

                        node.parent.centerLeftChild = new TwoFourTreeItem(node.value1);
                        node.parent.centerLeftChild.parent = node.parent;
                        node.parent.centerRightChild = new TwoFourTreeItem(node.value3);
                        node.parent.centerRightChild.parent = node.parent;
                        node.parent.centerChild = null;

                        node = node.parent;
                        //Possible Errors Again
                        if(node.value2 > value) {
                                node = node.centerLeftChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }

                        } else {
                                node = node.centerRightChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }
                        }
                    
                        return true;

                    } else {
                        /* Is right child, could be two/three node parent */
                        if(node.parent.isTwoNode()) {
                            node.parent.values = 2;
                            node.parent.value2 = liftValue;

                            node.parent.centerChild = new TwoFourTreeItem(node.value1);
                            node.parent.centerChild.parent = node.parent;
                            node.parent.rightChild = new TwoFourTreeItem(node.value3);
                            node.parent.rightChild.parent = node.parent;

                            node = node.parent;

                            if(node.value2 > value) {
                                node = node.centerChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }

                            } else {
                                node = node.rightChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }
                            }
                            return true;

                        } else {
                            //Is a three node
                            node.parent.values = 3;
                            node.parent.value3 = liftValue;

                            node.parent.centerLeftChild = node.parent.centerChild;
                            node.parent.centerLeftChild.parent = node.parent;
                            node.parent.centerChild = null;
                            node.parent.centerRightChild = new TwoFourTreeItem(node.value1);
                            node.parent.centerRightChild.parent = node.parent;
                            node.parent.rightChild = new TwoFourTreeItem(node.value3);
                            node.parent.rightChild.parent = node.parent;
                            
                            node = node.parent;
                         
                            if(node.value3 > value) {
                                node = node.centerRightChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }

                             } else {
                                node = node.rightChild;
                                node.values = 2;
                                if(node.value1 < value) {
                                    node.value2 = value;
                                } else {
                                    node.value2 = node.value1;
                                    node.value1 = value;
                                }
                            }

                            return true;
                        }
                    }
            }
    }

    /* Takes an integer value as input, traverses the tree looking for the value, returns true or false depending on if the value was found in the tree */
    public boolean hasValue(int value) {

        /* Start at root and traverse based on value */
        TwoFourTreeItem node = root;
        while(node != null) {

            if(node.isTwoNode()) {
                if(node.value1 == value) return true;
                else if(node.value1 > value) node = node.leftChild;
                else node = node.rightChild;
            }
            else if(node.isThreeNode()) {
                if(node.value1 == value || node.value2 == value) return true;
                else if(node.value1 > value) node = node.leftChild;
                else if(node.value2 > value) node = node.centerChild;
                else node = node.rightChild;
            }
            else {
                if(node.value1 == value || node.value2 == value || node.value3 == value) return true;
                else if(node.value1 > value) node = node.leftChild;
                else if(node.value2 > value) node = node.centerLeftChild;
                else if(node.value3 > value) node = node.centerRightChild;
                else node = node.rightChild;
            }
        
        }
        /* Will return false once the node we've reached is null, meaning value wasnt found */
        return false;
    }

    /* Takes an integer value as input, finds that integer value in the tree and removes it, returns a true or false value to determine if operation was successful or not */
    public boolean deleteValue(int value) {
        
        /* If the value was not in the tree, it is considered successful */
        if(!hasValue(value)) return true;

        /* temporary nodes used for finding successor and replacing nodes value when node is inside the tree and not a leaf */
        TwoFourTreeItem succesorNode, replacementNode;

        TwoFourTreeItem node = root;

        while(node != null) {
            /* merging node if its a two node */
            if(node.isTwoNode()) {
               node = merge(node);
            }
            /* Now checking if value is in node, and if its a leaf we can delete it */
            if((node.value1 == value || node.value2 == value || node.value3 == value) && node.isLeaf) {
                if(node.value1 == value) {
                    node.values--;
                    node.value1 = node.value2;
                    node.value2 = node.value3;
                    node.value3 = 0;
                    return true;
                }
                else if(node.value2 == value) {
                    node.values--;
                    node.value2 = node.value3;
                    node.value3 = 0;
                    return true;
                }
                else {
                    node.values--;
                    node.value3 = 0;
                    return true;
                }
            }
            /* If value is in the node and its not a leaf we need to find its successor */
            else if ((node.value1 == value || node.value2 == value || node.value3 == value)){
               /* This node can only be a three or four node since we merged */
                replacementNode = node;
                /* finding succesor based on node */
                if(replacementNode.isThreeNode()) {
                    /* need to move to right node and go as far left as possible to find succesor */
                    /* Replacing value1 of node */
                    if(replacementNode.value1 == value) {
                        succesorNode = node.centerChild;
                        succesorNode = merge(succesorNode);
                        if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }

                        while(succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }

                        }

                    }
                    /* We need to replace the second value in node */
                    else {
                        succesorNode = node.rightChild;
                        succesorNode = merge(succesorNode);
                        if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }

                        while(succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    }
                }
                else if(replacementNode.isTwoNode()) {
                    succesorNode = node.rightChild;
                    succesorNode = merge(succesorNode);
                    if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                        replacementNode = succesorNode;
                    }

                    while(succesorNode.leftChild != null) {
                        succesorNode = succesorNode.leftChild;
                        succesorNode = merge(succesorNode);
                        if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                    }
                }
                else {
                    /* Node is a four node and we need to find succesor and replace */
                    if(replacementNode.value1 == value) {
                        succesorNode = node.centerLeftChild;
                        succesorNode = merge(succesorNode);
                        if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }

                        while(succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    }
                    else if(replacementNode.value2 == value) {
                        succesorNode = node.centerRightChild;
                        succesorNode = merge(succesorNode);
                        if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }

                        while(succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }

                    }
                    else {
                        succesorNode = node.rightChild;
                        succesorNode = merge(succesorNode);
                        if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }

                        while(succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if(succesorNode.value1 == value || succesorNode.value2 == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    } 
                }

                /* Changing the values in succesor and node */
                if(replacementNode.value1 == value) {
                    replacementNode.value1 = succesorNode.value1;
                    succesorNode.values--;
                    succesorNode.value1 = succesorNode.value2;
                    succesorNode.value2 = succesorNode.value3;
                    succesorNode.value3 = 0;
                    return true;
                } else if(replacementNode.value2 == value) {
                    replacementNode.value2 = succesorNode.value1;
                    succesorNode.values--;
                    succesorNode.value1 = succesorNode.value2;
                    succesorNode.value2 = succesorNode.value3;
                    succesorNode.value3 = 0;
                    return true;
                } else {
                    replacementNode.value3 = succesorNode.value1;
                    succesorNode.values--;
                    succesorNode.value1 = succesorNode.value2;
                    succesorNode.value2 = succesorNode.value3;
                    succesorNode.value3 = 0;
                    return true;
                }
            }
            /* If the node doesnt contain the value we continue to traverse the tree */
            else {
                if(node.isThreeNode()) {
                    if(node.value1 > value) {
                        node = node.leftChild;
                        continue;
                    }
                    else if(node.value2 > value) {
                        node = node.centerChild;
                        continue;
                    }
                    else {
                        node = node.rightChild;
                        continue;
                    }
                }
                else {
                    if(node.value1 > value) {
                        node = node.leftChild;
                        continue;
                    }
                    else if(node.value2 > value) {
                        node = node.centerLeftChild;
                        continue;
                    }
                    else if(node.value3 > value) {
                        node = node.centerRightChild;
                        continue;
                    }
                    else {
                        node = node.rightChild;
                        continue;
                    }
                }
            }
        }

        /* If somehow we reach here return false as something went wrong */
        return false;
    }

    /* Takes a node as input, then the function determines if it needs to be merged/rotated based on the two four tree structure around it, and then it returns the merged/rotated node */
    public TwoFourTreeItem merge(TwoFourTreeItem node) {
        /* Function for merging the node */
        if(node == null) return null;

        /* Only need to take action if the node is a two node */
        if(node.isTwoNode()) {
            /* Addressing Root Node Changes if Necessary */
            if(node.isRoot()) {
                if(node.leftChild.isTwoNode() && node.rightChild.isTwoNode()) {
                    /* Fusing the root into a four node and re-assigning pointers */                        
                    TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.leftChild.value1, node.value1, node.rightChild.value1);
                    if(!node.leftChild.isLeaf && !node.rightChild.isLeaf) replacementNode.isLeaf = false;
                    replacementNode.leftChild = node.leftChild.leftChild;
                    replacementNode.leftChild.parent = replacementNode;

                    replacementNode.centerLeftChild = node.leftChild.rightChild;
                    replacementNode.centerLeftChild.parent = replacementNode;

                    replacementNode.centerRightChild = node.rightChild.leftChild;
                    replacementNode.centerRightChild.parent = replacementNode;

                    replacementNode.rightChild = node.rightChild.rightChild;
                    replacementNode.rightChild.parent = replacementNode;

                    node = replacementNode;
                    root = replacementNode;
                    return replacementNode;
                }
                return node;
            }
            /* Addresses all cases of the node being a left child */
            else if(node == node.parent.leftChild) {
                /* Parent can be two node / three node / four node so we perform whatever operation is needed */

                if(node.parent.isTwoNode()) {
                        /* siblings can be three or four node */
                        if(node.parent.rightChild.isThreeNode()) { 
                            
                            /* Creating new left child */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1);
                            replacementNode.isLeaf = node.isLeaf;
                            
                            if(!node.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;

                                replacementNode.centerChild = node.rightChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.rightChild.leftChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Changing parents value */
                            node.parent.value1 = node.parent.rightChild.value1;

                            /* Creating new right child */
                            TwoFourTreeItem replacementNodeRight = new TwoFourTreeItem(node.parent.rightChild.value2);
                            replacementNodeRight.isLeaf = node.parent.rightChild.isLeaf;

                            if(!node.parent.rightChild.isLeaf) {
                                replacementNodeRight.leftChild = node.parent.rightChild.centerChild;
                                replacementNodeRight.leftChild.parent = replacementNodeRight;

                                replacementNodeRight.rightChild = node.parent.rightChild.rightChild;
                                replacementNodeRight.rightChild.parent = replacementNodeRight;
                            }
        
                            /* Creating link for parents */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;

                            replacementNodeRight.parent = replacementNode.parent;
                            replacementNodeRight.parent.rightChild = replacementNodeRight;

                            return replacementNode;
                        } 
                        else {
                            /* Sibling is four node */                                                                  /* When implementing, this threw an ERROR LINE 940 left child null */
                           
                            /* Creating a new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;
                                
                                replacementNode.centerChild = node.rightChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.rightChild.leftChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Rotating the parents value */
                            node.parent.value1 = node.parent.rightChild.value1;

                            /* Creating a new right node */
                            TwoFourTreeItem replacementNodeRight = new TwoFourTreeItem(node.parent.rightChild.value2, node.parent.rightChild.value3);
                            replacementNodeRight.isLeaf = node.parent.rightChild.isLeaf;


                            if(!replacementNodeRight.isLeaf) {
                                replacementNodeRight.leftChild = node.parent.rightChild.centerLeftChild;
                                replacementNodeRight.leftChild.parent = replacementNodeRight;

                                replacementNodeRight.centerChild = node.parent.rightChild.centerRightChild;
                                replacementNodeRight.centerChild.parent = replacementNodeRight;

                                replacementNodeRight.rightChild = node.parent.rightChild.rightChild;
                                replacementNodeRight.rightChild.parent = replacementNodeRight;
                            }
                            
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;

                            replacementNodeRight.parent = replacementNode.parent;
                            replacementNodeRight.parent.rightChild = replacementNodeRight;

                            return replacementNode;
                        }
                }
                else if(node.parent.isThreeNode()) {
                    
                        /* Need to check what type the siblings are */
                        if(node.parent.centerChild.isTwoNode()) {
                            /* Fusing three two nodes into a four node */    

                            /* Creating new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1, node.parent.centerChild.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;

                                replacementNode.centerLeftChild = node.rightChild;
                                replacementNode.centerLeftChild.parent = replacementNode;

                                replacementNode.centerRightChild = node.parent.centerChild.leftChild;
                                replacementNode.centerRightChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.centerChild.rightChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Adjusting parent node */
                            node.parent.values--;
                            node.parent.value1 = node.parent.value2;
                            node.parent.value2 = 0; // MAY BE REDUNDANT CHECK

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;
                            replacementNode.parent.centerChild = null;

                            return replacementNode;
                        }
                        else if(node.parent.centerChild.isThreeNode()) {
                            /* Rotating */
                           
                            /* Creating new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1);
                            replacementNode.isLeaf = node.isLeaf;
                            
                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;

                                replacementNode.centerChild = node.rightChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.centerChild.leftChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Adjusting parent node values */
                            node.parent.value1 = node.parent.centerChild.value1;

                            /* Creating new center node */
                            TwoFourTreeItem replacementNodeCenter = new TwoFourTreeItem(node.parent.centerChild.value2);
                            replacementNodeCenter.isLeaf = node.parent.centerChild.isLeaf;

                            if(!replacementNodeCenter.isLeaf) {
                                replacementNodeCenter.leftChild = node.parent.centerChild.centerChild;
                                replacementNodeCenter.leftChild.parent = replacementNodeCenter;
                                
                                replacementNodeCenter.rightChild = node.parent.centerChild.rightChild;
                                replacementNodeCenter.rightChild.parent = replacementNodeCenter;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;

                            replacementNodeCenter.parent = replacementNode.parent;
                            replacementNodeCenter.parent.centerChild = replacementNodeCenter;
   
                            return replacementNode;
                        } 
                        else {
                            /* Rotating again - FOUR NODE */
                            
                            /* Creating a new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;

                                replacementNode.centerChild = node.rightChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.centerChild.leftChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Adjusting parents values */
                            node.parent.value1 = node.parent.centerChild.value1;

                            /* Creating new center node */
                            TwoFourTreeItem replacementNodeCenter = new TwoFourTreeItem(node.parent.centerChild.value2, node.parent.centerChild.value3);
                            replacementNodeCenter.isLeaf = node.parent.centerChild.isLeaf;

                            if(!replacementNodeCenter.isLeaf) {
                                replacementNodeCenter.leftChild = node.parent.centerChild.centerLeftChild;
                                replacementNodeCenter.leftChild.parent = replacementNodeCenter;

                                replacementNodeCenter.centerChild = node.parent.centerChild.centerRightChild;
                                replacementNodeCenter.centerChild.parent = replacementNodeCenter;

                                replacementNodeCenter.rightChild = node.parent.centerChild.rightChild;
                                replacementNodeCenter.rightChild.parent = replacementNodeCenter;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;

                            replacementNodeCenter.parent = replacementNode.parent;
                            replacementNodeCenter.parent.centerChild = replacementNodeCenter;

                            return replacementNode;
                        }
                }
                else {
                        /* PARENT IS A FOUR NODE */
                        /* need to check what type the siblings are */
                        if(node.parent.centerLeftChild.isTwoNode()) {
                            /* Fusing into a four node */

                            /* Creating new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1, node.parent.centerLeftChild.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;
                                
                                replacementNode.centerLeftChild = node.rightChild;
                                replacementNode.centerLeftChild.parent = replacementNode;

                                replacementNode.centerRightChild = node.parent.centerLeftChild.leftChild;
                                replacementNode.centerRightChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.centerLeftChild.rightChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Adjusting parent values and size */
                            node.parent.values = 2;
                            node.parent.value1 = node.parent.value2;
                            node.parent.value2 = node.parent.value3;
                            node.parent.value3 = 0;  

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;
                            replacementNode.parent.centerChild = node.parent.centerRightChild;
                            replacementNode.parent.centerLeftChild = null;
                            replacementNode.parent.centerRightChild = null;

                            return replacementNode;
                        } 
                        else if(node.parent.centerLeftChild.isThreeNode()) {
                            /* Rotating */
                            
                            /* Creating a new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;

                                replacementNode.centerChild = node.rightChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.centerLeftChild.leftChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Adjusting parent values */
                            node.parent.value1 = node.parent.centerLeftChild.value1;

                            /* Creating a new center left node */
                            TwoFourTreeItem replacementNodeCenterLeft = new TwoFourTreeItem(node.parent.centerLeftChild.value2);
                            replacementNodeCenterLeft.isLeaf = node.parent.centerLeftChild.isLeaf;

                            if(!replacementNodeCenterLeft.isLeaf) {
                                replacementNodeCenterLeft.leftChild = node.parent.centerLeftChild.centerChild;
                                replacementNodeCenterLeft.leftChild.parent = replacementNodeCenterLeft;

                                replacementNodeCenterLeft.rightChild = node.parent.centerLeftChild.rightChild;
                                replacementNodeCenterLeft.rightChild.parent = replacementNodeCenterLeft;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;

                            replacementNodeCenterLeft.parent = replacementNode.parent;
                            replacementNodeCenterLeft.parent.centerLeftChild = replacementNodeCenterLeft;

                            return replacementNode;
                        }
                        else {
                            /* Rotating because its sibling is a four node*/

                            /* Creating a new left node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.leftChild = node.leftChild;
                                replacementNode.leftChild.parent = replacementNode;

                                replacementNode.centerChild = node.rightChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.rightChild = node.parent.centerLeftChild.leftChild;
                                replacementNode.rightChild.parent = replacementNode;
                            }

                            /* Rotating parent value */
                            node.parent.value1 = node.parent.centerLeftChild.value1;

                            /* Creating new center left node */
                            TwoFourTreeItem replacementNodeCenterLeft = new TwoFourTreeItem(node.parent.centerLeftChild.value2, node.parent.centerLeftChild.value3);
                            replacementNodeCenterLeft.isLeaf = node.parent.centerLeftChild.isLeaf;

                            if(!replacementNodeCenterLeft.isLeaf) {
                                replacementNodeCenterLeft.leftChild = node.parent.centerLeftChild.centerLeftChild;
                                replacementNodeCenterLeft.leftChild.parent = replacementNodeCenterLeft;

                                replacementNodeCenterLeft.centerChild = node.parent.centerLeftChild.centerRightChild;
                                replacementNodeCenterLeft.centerChild.parent = replacementNodeCenterLeft;

                                replacementNodeCenterLeft.rightChild = node.parent.centerLeftChild.rightChild;
                                replacementNodeCenterLeft.rightChild.parent = replacementNodeCenterLeft;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.leftChild = replacementNode;

                            replacementNodeCenterLeft.parent = replacementNode.parent;
                            replacementNodeCenterLeft.parent.centerLeftChild = replacementNodeCenterLeft;
  
                            return replacementNode;
                        }
                    }
            }
            /* Addresses all cases of the node being a center left child */
            else if(node == node.parent.centerLeftChild) {
                /* Parent can be only a four node -- Sibling can be 2-4
                * Therefor we are merging and rotating
                */
                if(node.parent.centerRightChild.isTwoNode()) {

                        /* Creating a new center left child */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value2, node.parent.centerRightChild.value1);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerLeftChild = node.rightChild;
                            replacementNode.centerLeftChild.parent = replacementNode;

                            replacementNode.centerRightChild = node.parent.centerRightChild.leftChild;
                            replacementNode.centerRightChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.centerRightChild.rightChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent node values and size */
                        node.parent.values = 2;
                        node.parent.value2 = node.parent.value3;
                        node.parent.value3 = 0;

                        /* Adjusting parent pointers */
                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerChild = replacementNode;
                        replacementNode.parent.centerRightChild = null;
                        replacementNode.parent.centerLeftChild = null;

                        return replacementNode;
                    }
                else if(node.parent.centerRightChild.isThreeNode()) {
                      
                        /* Creating new center left node */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value2);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.centerRightChild.leftChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Changing parent value */
                        node.parent.value2 = node.parent.centerRightChild.value1;
                        
                        /* Creating new center right node */
                        TwoFourTreeItem replacementNodeCenterRight = new TwoFourTreeItem(node.parent.centerRightChild.value2);
                        replacementNodeCenterRight.isLeaf = node.parent.centerRightChild.isLeaf;
                       
                        if(!replacementNodeCenterRight.isLeaf) {
                            replacementNodeCenterRight.leftChild = node.parent.centerRightChild.centerChild;
                            replacementNodeCenterRight.leftChild.parent = replacementNodeCenterRight;

                            replacementNodeCenterRight.rightChild = node.parent.centerRightChild.rightChild;
                            replacementNodeCenterRight.rightChild.parent = replacementNodeCenterRight;
                        }

                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerLeftChild = replacementNode;

                        replacementNodeCenterRight.parent = replacementNode.parent;
                        replacementNodeCenterRight.parent.centerRightChild = replacementNodeCenterRight;
                       
                        return replacementNode;
                    }
                else {
                        /* The right sibling is a four node!!! */
                        
                        /* Creating new center left node */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value2);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent = replacementNode;
                            
                            replacementNode.rightChild = node.parent.centerRightChild.leftChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent values */
                        node.parent.value2 = node.parent.centerRightChild.value1;

                        /* Creating new center right node */
                        TwoFourTreeItem replacementNodeCenterRight = new TwoFourTreeItem(node.parent.centerLeftChild.value2, node.parent.centerRightChild.value3);
                        replacementNodeCenterRight.isLeaf = node.parent.centerRightChild.isLeaf;

                        if(!replacementNodeCenterRight.isLeaf) {
                            replacementNodeCenterRight.leftChild = node.parent.centerRightChild.centerLeftChild;
                            replacementNodeCenterRight.leftChild.parent = replacementNodeCenterRight;
                            
                            replacementNodeCenterRight.centerChild = node.parent.centerRightChild.centerRightChild;
                            replacementNodeCenterRight.centerChild.parent = replacementNodeCenterRight;

                            replacementNodeCenterRight.rightChild = node.parent.centerRightChild.rightChild;
                            replacementNodeCenterRight.rightChild.parent = replacementNodeCenterRight;
                        }

                        /* Adjusting parent pointers */
                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerLeftChild = replacementNode;

                        replacementNodeCenterRight.parent = replacementNode.parent;
                        replacementNodeCenterRight.parent.centerRightChild = replacementNodeCenterRight;

                        return replacementNode;
                    }
            }
            /* Addresses all cases of the node being a center child */
            else if(node == node.parent.centerChild) {
                /* Parent can only be a three node -- Siblings can be 2-4 */
                if(node.parent.rightChild.isTwoNode()) {
                        
                        /* Creating new right node since this one will be pushed right */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value2, node.parent.rightChild.value1);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            
                            replacementNode.centerLeftChild = node.rightChild;
                            replacementNode.centerLeftChild.parent = replacementNode;

                            replacementNode.centerRightChild = node.parent.rightChild.leftChild;
                            replacementNode.centerRightChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.rightChild.rightChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent size and values */
                        node.parent.values = 1;
                        node.parent.value2 = 0;

                        /* Adjusting pointers */
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNode.parent.centerChild = null;
                        return replacementNode;
                    } 
                else if(node.parent.rightChild.isThreeNode()) {

                        /* Creating new center node */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value2);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.rightChild.leftChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }
                        /* Adjusting parent value */
                        node.parent.value2 = node.parent.rightChild.value1;

                        /* Creating new right node */
                        TwoFourTreeItem replacementNodeRight = new TwoFourTreeItem(node.parent.rightChild.value2);
                        replacementNodeRight.isLeaf = node.parent.rightChild.isLeaf;

                        if(!replacementNodeRight.isLeaf) {
                            replacementNodeRight.leftChild = node.parent.rightChild.centerChild;
                            replacementNodeRight.leftChild.parent = replacementNodeRight;

                            replacementNodeRight.rightChild = node.parent.rightChild.rightChild;
                            replacementNodeRight.rightChild.parent = replacementNodeRight;
                        }

                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerChild = replacementNode;

                        replacementNodeRight.parent = replacementNode.parent;
                        replacementNodeRight.parent.rightChild = replacementNodeRight;
                      
                        return replacementNode;
                } 
                else {
                        /* Sibling is a four node */
                       
                        /* Creating a new center node */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value2);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent = replacementNode;
                            
                            replacementNode.rightChild = node.parent.rightChild.leftChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent values */
                        node.parent.value2 = node.parent.rightChild.value1;

                        /* Creating a new right node */
                        TwoFourTreeItem replacementNodeRight = new TwoFourTreeItem(node.parent.rightChild.value2, node.parent.rightChild.value3);
                        replacementNodeRight.isLeaf = node.parent.rightChild.isLeaf; 
                        
                        if(!replacementNodeRight.isLeaf) {
                            replacementNodeRight.leftChild = node.parent.rightChild.centerLeftChild;
                            replacementNodeRight.leftChild.parent = replacementNodeRight;

                            replacementNodeRight.centerChild = node.parent.rightChild.centerRightChild;
                            replacementNodeRight.centerChild.parent = replacementNodeRight;

                            replacementNodeRight.rightChild = node.parent.rightChild.rightChild;
                            replacementNodeRight.rightChild.parent = replacementNodeRight;
                        }

                        /* Adjusting parents pointers */
                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerChild = replacementNode;

                        replacementNodeRight.parent = replacementNode.parent;
                        replacementNodeRight.parent.rightChild = replacementNodeRight;

                        return replacementNode;
                    }
            }
            /* Addresses all cases of the node being a cetner right child */
            else if(node == node.parent.centerRightChild) {
                /* Parent can be only a four node -- Siblings can be 2-4
                * Therefor merging nodes and/or rotating
                */
                if(node.parent.rightChild.isTwoNode()) {
                    
                        /* Creating a new right node since this node will merge to the right*/
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value3, node.parent.rightChild.value1);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerLeftChild = node.rightChild;
                            replacementNode.centerLeftChild.parent = replacementNode;

                            replacementNode.centerRightChild = node.parent.rightChild.leftChild;
                            replacementNode.centerRightChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.rightChild.rightChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent values */
                        node.parent.values = 2;
                        node.parent.value3 = 0;

                        /* Adjusting parent pointers */
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNode.parent.centerChild = replacementNode.parent.centerLeftChild;
                        replacementNode.parent.centerLeftChild = null;
                        replacementNode.parent.centerRightChild = null;

                        return replacementNode;
                    }
                else if(node.parent.rightChild.isThreeNode()) {
                       
                        /* Creating new center right child */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value3);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;

                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.rightChild.leftChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent values */
                        node.parent.value3 = node.parent.rightChild.value1;

                        /* Creating new right child node */ 
                        TwoFourTreeItem replacementNodeRight = new TwoFourTreeItem(node.parent.rightChild.value2);
                        replacementNodeRight.isLeaf = node.parent.rightChild.isLeaf;
                        
                        if(!replacementNodeRight.isLeaf) {
                            replacementNodeRight.leftChild = node.parent.rightChild.centerChild;
                            replacementNodeRight.leftChild.parent = replacementNodeRight;

                            replacementNodeRight.rightChild = node.parent.rightChild.rightChild;
                            replacementNodeRight.rightChild.parent = replacementNodeRight;
                        }

                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerRightChild = replacementNode;

                        replacementNodeRight.parent = replacementNode.parent;
                        replacementNodeRight.parent.rightChild = replacementNodeRight;

                        return replacementNode;
                    }
                else {
                        /* right sibling is four node */
                        
                        /* Creating a new center right node */
                        TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.value1, node.parent.value3);
                        replacementNode.isLeaf = node.isLeaf;

                        if(!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent = replacementNode;

                            replacementNode.rightChild = node.parent.rightChild.leftChild;
                            replacementNode.rightChild.parent = replacementNode;
                        }

                        /* Adjusting parent values */
                        node.parent.value3 = node.rightChild.value1;
                        
                        /* Creating a new right node */
                        TwoFourTreeItem replacementNodeRight = new TwoFourTreeItem(node.parent.rightChild.value2, node.parent.rightChild.value3);
                        replacementNodeRight.isLeaf = node.isLeaf;

                        if(!replacementNodeRight.isLeaf) {
                            replacementNodeRight.leftChild = node.parent.rightChild.centerLeftChild;
                            replacementNodeRight.leftChild.parent = replacementNodeRight;

                            replacementNodeRight.centerChild = node.parent.rightChild.centerRightChild;
                            replacementNodeRight.centerChild.parent = replacementNodeRight;

                            replacementNodeRight.rightChild = node.parent.rightChild.rightChild;
                            replacementNodeRight.rightChild.parent = replacementNodeRight;
                        }

                        /* Adjusting parents pointers */
                        replacementNode.parent = node.parent;
                        replacementNode.parent.centerRightChild = replacementNode;

                        replacementNodeRight.parent = replacementNode.parent;
                        replacementNodeRight.parent.rightChild = replacementNodeRight;

                        return replacementNode;
                    }
            }
            /* Addresses all cases of the node being a right child */
            else {
            /* is right child 
            * Parent can be a two node, three node, or four node
            */
                if(node.parent.isTwoNode()) {
                    /* siblings can be three or four node */
                    if(node.parent.leftChild.isThreeNode()) {
                 
                            /* Creating a new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.value1, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerChild = node.leftChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.leftChild.rightChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }
                
                            /* Adjusting parent values */
                            node.parent.value1 = node.parent.leftChild.value2;

                            /* Creating a new left node */
                            TwoFourTreeItem replacementNodeLeft = new TwoFourTreeItem(node.parent.leftChild.value1);
                            replacementNodeLeft.isLeaf = node.parent.leftChild.isLeaf;

                            if(!replacementNodeLeft.isLeaf) {
                                replacementNodeLeft.rightChild = node.parent.leftChild.centerChild;
                                replacementNodeLeft.rightChild.parent = replacementNodeLeft;

                                replacementNodeLeft.leftChild = node.parent.leftChild.leftChild;
                                replacementNodeLeft.leftChild.parent = replacementNodeLeft;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;

                            replacementNodeLeft.parent = replacementNode.parent;
                            replacementNodeLeft.parent.leftChild = replacementNodeLeft;

                            return replacementNode;
                        } 
                    else {
                            /* sibling is four node */
                                                       
                            /* Creating a new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.value1, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerChild = node.leftChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.leftChild.rightChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting parent values */
                            node.parent.value1 = node.parent.leftChild.value3;

                            /* Creating a new left node */
                            TwoFourTreeItem replacementNodeLeft = new TwoFourTreeItem(node.parent.leftChild.value1, node.parent.leftChild.value2);
                            replacementNodeLeft.isLeaf = node.parent.leftChild.isLeaf;
                           

                            if(!replacementNodeLeft.isLeaf) {
                                replacementNodeLeft.rightChild = node.parent.leftChild.centerRightChild;
                                replacementNodeLeft.rightChild.parent = replacementNodeLeft;

                                replacementNodeLeft.centerChild = node.parent.leftChild.centerLeftChild;
                                replacementNodeLeft.centerChild.parent = replacementNodeLeft;

                                replacementNodeLeft.leftChild = node.parent.leftChild.leftChild;
                                replacementNodeLeft.leftChild.parent = replacementNodeLeft;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;

                            replacementNodeLeft.parent = replacementNode.parent;
                            replacementNodeLeft.parent.leftChild = replacementNodeLeft;

                            return replacementNode;
                        }
                }
                else if(node.parent.isThreeNode()) {
                        if(node.parent.centerChild.isTwoNode()) {
                           
                            /* Creating new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.centerChild.value1, node.parent.value2, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerRightChild = node.leftChild;
                                replacementNode.centerRightChild.parent = replacementNode;

                                replacementNode.centerLeftChild = node.parent.centerChild.rightChild;
                                replacementNode.centerLeftChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.centerChild.leftChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting parent values */
                            node.parent.values = 1;
                            node.parent.value2 = 0;
                            
                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;
                            replacementNode.parent.centerChild = null;
                            return replacementNode;
                        }
                        else if(node.parent.centerChild.isThreeNode()) {
                            
                            /* Creating a new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.value2, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerChild = node.leftChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.centerChild.rightChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting parent values */
                            node.parent.value2 = node.parent.centerChild.value2;
                            
                            /* Creating new center node */ 
                            TwoFourTreeItem replacementNodeCenter = new TwoFourTreeItem(node.parent.centerChild.value1);
                            replacementNodeCenter.isLeaf = node.parent.centerChild.isLeaf;

                            if(!replacementNodeCenter.isLeaf) {
                                replacementNodeCenter.rightChild = node.parent.centerChild.centerChild;
                                replacementNodeCenter.rightChild.parent = replacementNodeCenter;

                                replacementNodeCenter.leftChild = node.parent.centerChild.leftChild;
                                replacementNodeCenter.leftChild.parent = replacementNodeCenter;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;

                            replacementNodeCenter.parent = replacementNode.parent;
                            replacementNodeCenter.parent.centerChild = replacementNodeCenter;
                            return replacementNode;
                        }
                        else {
                            /* left sibling is a four node */

                            /* Creating new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.value2, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerChild = node.leftChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.centerChild.rightChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting parent values */
                            node.parent.value2 = node.parent.centerChild.value3;

                            /* Creating a new center node */
                            TwoFourTreeItem replacementNodeCenter = new TwoFourTreeItem(node.parent.centerChild.value1, node.parent.centerChild.value2);
                            replacementNodeCenter.isLeaf = node.parent.centerChild.isLeaf;

                            if(!replacementNodeCenter.isLeaf) {
                                replacementNodeCenter.rightChild = node.parent.centerChild.centerRightChild;
                                replacementNodeCenter.rightChild.parent = replacementNodeCenter;

                                replacementNodeCenter.centerChild = node.parent.centerChild.centerLeftChild;
                                replacementNodeCenter.centerChild.parent = replacementNodeCenter;

                                replacementNodeCenter.leftChild = node.parent.centerChild.leftChild;
                                replacementNodeCenter.leftChild.parent = replacementNodeCenter;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;

                            replacementNodeCenter.parent = replacementNode.parent;
                            replacementNodeCenter.parent.centerChild = replacementNodeCenter;
                            return replacementNode;
                        }
                }
                else {
                        /* parent is a four node */
                        if(node.parent.centerRightChild.isTwoNode()) {
                            
                            /* Creating a new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.centerRightChild.value1, node.parent.value3, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;
                                
                                replacementNode.centerRightChild = node.leftChild;
                                replacementNode.centerRightChild.parent = replacementNode;

                                replacementNode.centerLeftChild = node.parent.centerRightChild.rightChild;
                                replacementNode.centerLeftChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.centerRightChild.leftChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting parent values and size */
                            node.parent.values = 2;
                            node.parent.value3 = 0;

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;
                            replacementNode.parent.centerChild = replacementNode.parent.centerLeftChild;
                            replacementNode.parent.centerLeftChild = null;
                            replacementNode.parent.centerRightChild = null;

                            return replacementNode;
                        }
                        else if(node.parent.centerRightChild.isThreeNode()) {
                            
                            /* Creating a new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.value3, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerChild = node.leftChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.centerRightChild.rightChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting parent values */
                            node.parent.value3 = node.parent.centerRightChild.value2;

                            /* Creating a new center right node */
                            TwoFourTreeItem replacementNodeCenterRight = new TwoFourTreeItem(node.parent.centerRightChild.value1);
                            replacementNodeCenterRight.isLeaf = node.parent.centerRightChild.isLeaf;

                            if(!replacementNodeCenterRight.isLeaf) {
                                replacementNodeCenterRight.rightChild = node.parent.centerRightChild.centerChild;
                                replacementNodeCenterRight.rightChild.parent = replacementNodeCenterRight;

                                replacementNodeCenterRight.leftChild = node.parent.centerRightChild.leftChild;
                                replacementNodeCenterRight.leftChild.parent = replacementNodeCenterRight;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;

                            replacementNodeCenterRight.parent = replacementNode.parent;
                            replacementNodeCenterRight.parent.centerRightChild = replacementNodeCenterRight;
                            return replacementNode;
                        }
                        else {
                            /* left sibling is a four node */
                        
                            /* Creating a new right node */
                            TwoFourTreeItem replacementNode = new TwoFourTreeItem(node.parent.value3, node.value1);
                            replacementNode.isLeaf = node.isLeaf;

                            if(!replacementNode.isLeaf) {
                                replacementNode.rightChild = node.rightChild;
                                replacementNode.rightChild.parent = replacementNode;

                                replacementNode.centerChild = node.leftChild;
                                replacementNode.centerChild.parent = replacementNode;

                                replacementNode.leftChild = node.parent.centerRightChild.rightChild;
                                replacementNode.leftChild.parent = replacementNode;
                            }

                            /* Adjusting the parent values */
                            node.parent.value3 = node.parent.centerRightChild.value3;

                            /* Creating a new center right node */
                            TwoFourTreeItem replacementNodeCenterRight = new TwoFourTreeItem(node.parent.centerRightChild.value1, node.parent.centerRightChild.value2);
                            replacementNodeCenterRight.isLeaf = node.parent.centerRightChild.isLeaf;

                            if(!replacementNodeCenterRight.isLeaf) {
                                replacementNodeCenterRight.rightChild = node.parent.centerRightChild.centerRightChild;
                                replacementNodeCenterRight.rightChild.parent = replacementNodeCenterRight;

                                replacementNodeCenterRight.centerChild = node.parent.centerRightChild.centerLeftChild;
                                replacementNodeCenterRight.centerChild.parent = replacementNodeCenterRight;

                                replacementNodeCenterRight.leftChild = node.parent.centerRightChild.leftChild;
                                replacementNodeCenterRight.leftChild.parent = replacementNodeCenterRight;
                            }

                            /* Adjusting parent pointers */
                            replacementNode.parent = node.parent;
                            replacementNode.parent.rightChild = replacementNode;

                            replacementNodeCenterRight.parent = replacementNode.parent; 
                            replacementNodeCenterRight.parent.centerRightChild = replacementNodeCenterRight;

                            return replacementNode;
                        }
                    }
            }
        }

        /* If its not a two node, no change is needed */
        return node;   
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }
}
