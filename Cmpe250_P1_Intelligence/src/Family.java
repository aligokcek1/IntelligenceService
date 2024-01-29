import java.util.*;

public class Family {
    private static final int ALLOWED_IMBALANCE = 1;
    private Member boss;

    public Family() {    //CONSTRUCTOR OF FAMILY
        boss = null;
    }

    //TREE NODE(MEMBER)
    private class Member {
        String name;
        int height;
        float gms;
        Member left;
        Member right;

        Member(float gms, String name) {        //CONSTRUCTORS OF MEMBER NODES
            this(gms, name, null, null);
        }

        Member(float gms, String name, Member lt, Member rt) {
            this.name = name;
            this.gms = gms;
            this.left = lt;
            this.right = rt;
            this.height = 0;
        }


    }

    //MEMBER_IN METHODS
    public void insert(float gms, String name) {
        boss = insert(gms, name, boss);
    }

    //INTERNAL METHOD FOR INSERTING
    private Member insert(float gms, String name, Member root) {
        if (root == null)
            return new Member(gms, name);
        Main.out.println(root.name + " welcomed " + name);
        int compareResult = Float.compare(gms, root.gms);

        if (compareResult < 0)
            root.left = insert(gms, name, root.left);
        else if (compareResult > 0)
            root.right = insert(gms, name, root.right);
        else
            ;
        return balance(root);
    }

    //MEMBER_OUT METHODS
    public void remove(Float gms) {
        boss = remove(gms, boss);
        isRemovePrinted = true;   //CHECK IF REMOVMENT INFO IS PRINTED OR NOT
    }

    public boolean isRemovePrinted = true;

    private Member remove(Float gms, Member t) {
        if (t == null)
            return t;

        int compareResult = Float.compare(gms, t.gms);

        if (compareResult < 0)
            t.left = remove(gms, t.left);
        else if (compareResult > 0)
            t.right = remove(gms, t.right);

        else if (t.left != null && t.right != null) {
            Member minMember = findMin(t.right);
            if (isRemovePrinted) {
                Main.out.println(t.name + " left the family, replaced by " + minMember.name);
                isRemovePrinted = false;
            }
            t.gms = minMember.gms;
            t.name = minMember.name;
            t.right = remove(t.gms, t.right);
        } else {

            if (t.left != null) {
                if (isRemovePrinted) {
                    Main.out.println(t.name + " left the family, replaced by " + t.left.name);
                    isRemovePrinted = false;
                }
                t.gms = t.left.gms;
                t.name = t.left.name;
                t.left = null;

            } else if (t.right != null) {
                if (isRemovePrinted) {
                    Main.out.println(t.name + " left the family, replaced by " + t.right.name);
                    isRemovePrinted = false;
                }
                t.gms = t.right.gms;
                t.name = t.right.name;
                t.right = null;

            } else {
                if (isRemovePrinted) {
                    Main.out.println(t.name + " left the family, replaced by nobody");
                    isRemovePrinted = false;
                }
                t = null;
            }
        }
        return balance(t);
    }

    private Member findMin(Member root) {
        if (root == null)
            return root;

        while (root.left != null)
            root = root.left;
        return root;
    }

    //STANDARD ROTATING METHODS
    private Member balance(Member root) {

        if (root == null)
            return root;

        if (height(root.left) - height(root.right) > ALLOWED_IMBALANCE)
            if (height(root.left.left) >= height(root.left.right))
                root = rotateWithLeftChild(root);
            else
                root = doubleWithLeftChild(root);
        else if (height(root.right) - height(root.left) > ALLOWED_IMBALANCE)
            if (height(root.right.right) >= height(root.right.left))
                root = rotateWithRightChild(root);
            else
                root = doubleWithRightChild(root);

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return root;
    }

    private int height(Member t) {
        return t == null ? -1 : t.height;
    }
    // ROTATORS
    private Member rotateWithLeftChild(Member k2) {
        Member k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private Member rotateWithRightChild(Member k1) {
        Member k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }

    private Member doubleWithLeftChild(Member k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Member doubleWithRightChild(Member k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    //INTEL_RANK METHODS
    public void rankAnalysis(float gms) {
        int targetLevel = findRank(this.boss, gms);
        Main.out.print("Rank Analysis Result:");
        printNodesAtLevel(this.boss, targetLevel, 0);
        Main.out.print("\n");
    }

    //INTERNAL METHOD FOR RANK ANALYSIS(BASIC DEPTH CALCULATOR TO FIND THE TARGET LEVEL)
    private int findRank(Member root, float gms) {
        if (root == null)
            return -1;
        int dist = -1;

        if ((root.gms == gms) ||

                (dist = findRank(root.left, gms)) >= 0 ||

                (dist = findRank(root.right, gms)) >= 0)

            return dist + 1;

        return dist;
    }

    //PRINTER FOR THE GIVEN(TARGET) LEVEL
    private void printNodesAtLevel(Member root, int targetLevel, int currentLevel) {
        if (root == null) {
            return;
        }
        if (currentLevel == targetLevel) {
            String dumstr = String.format(Locale.US, "%.3f", root.gms);
            Main.out.print(" " + root.name + " " + dumstr);
        } else {

            printNodesAtLevel(root.left, targetLevel, currentLevel + 1);
            printNodesAtLevel(root.right, targetLevel, currentLevel + 1);
        }
    }

    //INTEL_TARGET METHODS
    public void findTarget(float n1, float n2) {
        Member target = findTarget(boss, n1, n2);
        Main.out.print("Target Analysis Result: ");
        Main.out.print(target.name + " ");
        String s = String.format(Locale.US, "%.03f", target.gms);
        Main.out.printf(s + "\n");
    }

    //INTERNAL METHOD TO FIND LOWEST_COMMON_ANCESTOR
    private Member findTarget(Member root, float n1, float n2) {
        if (root == null)
            return null;

        if (root.gms == n1 || root.gms == n2)
            return root;

        Member left_lca = findTarget(root.left, n1, n2);
        Member right_lca = findTarget(root.right, n1, n2);


        if (left_lca != null && right_lca != null)
            return root;

        return (left_lca != null) ? left_lca : right_lca;
    }

    // INTEL_DIVIDE METHODS
    public void divide() {
        int[] results = findMaxIndependentSetSize(this.boss);
        int output = Math.max(results[0], results[1]);
        Main.out.println("Division Analysis Result: " + output);
    }

    // INTERNAL METHOD FOR DIVIDE() WHICH FINDS THE MAX INDEPENDENT SET AND ITS SIZE
    private int[] findMaxIndependentSetSize(Member root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        int[] leftResults = findMaxIndependentSetSize(root.left);
        int[] rightResults = findMaxIndependentSetSize(root.right);

        int included = 1 + leftResults[1] + rightResults[1];


        int notIncluded = Math.max(leftResults[0], leftResults[1]) + Math.max(rightResults[0], rightResults[1]);

        return new int[]{included, notIncluded};
    }

}
