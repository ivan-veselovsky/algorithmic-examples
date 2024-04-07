package edu.findlongestpath;


// Time: O(n)

//class InterviewSolution {
//
//    public static int findLongestPath(Node root) {
//        cacheDtaForNode(root);
//
//        return getMaxForSubtree(root);
//    }
//
//
//    private static int getMaxForSubtree(Node node) {
//        if (node == null) {
//            return 0;
//        }
//        int maxNode = getMaxPathLength(node);
//
//        int maxLeftSubtree = getMaxForSubtree(node.left());
//        int maxRightSubtree = getMaxForSubtree(node.right());
//
//        int maxOfChildSubtrees = Math.max(maxLeftSubtree, maxRightSubtree);
//        return Math.max(maxOfChildSubtrees, maxNode);
//    }
//
//    private static int getMaxPathLength(Node node) {
//        return node.leftSubtreeHeight + node.rightSubtreeHeight;
//    }
//
//    private static void cacheDtaForNode(Node node) {
//        if (node.leftSubtreeHeight == null) {
//            node.leftSubtreeHeight = getLeftSubtreeHeight(node);
//        }
//        if (node.rightSubtreeHeight == null) {
//            node.rightSubtreeHeight = getRightSubtreeHeight(node);
//        }
//    }
//
//    private static int getLeftSubtreeHeight(Node node) {
//        if (node == null) {
//            return -1;
//        }
//        if (node.leftSubtreeHeight != null) {
//            return node.leftSubtreeHeight;
//        }
//        node.leftSubtreeHeight = 1 + Math.max(
//                getLeftSubtreeHeight(node.left()),
//                getRightSubtreeHeight(node.left())
//        );
//        return node.leftSubtreeHeight;
//    }
//
//    private static int getRightSubtreeHeight(Node node) {
//        if (node == null) {
//            return -1;
//        }
//        if (node.rightSubtreeHeight != null) {
//            return node.rightSubtreeHeight;
//        }
//        node.rightSubtreeHeight = 1 + Math.max(
//                getLeftSubtreeHeight(node.right()),
//                getRightSubtreeHeight(node.right())
//        );
//        return node.rightSubtreeHeight;
//    }
//}
//
