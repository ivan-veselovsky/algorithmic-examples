//package edu.recursive_top_to_bottom;
//
//import java.util.Iterator;
//
//public record Parser(Iterator<Lexema> lexemas) {
//
//    public Node parse() {
//        return process(tokens.getFirst(), null);
//    }
//
//    private Lexema next() {
//        if (lexemas.hasNext()) {
//            return lexemas.next();
//        }
//        return null;
//    }
//
//    Node process(Lexema nextToken, Node previousNode) {
////        if (currentNode == null) {
////            currentNode = new Node();
////        }
//
//        if (nextToken instanceof Atom) {
//            if (previousNode == null) {
//                return new Node(nextToken); // leaf
//            }
//            if (previousNode.isAtom()) {
//                throw new IllegalStateException("Operation expected");
//            }
//            if (previousNode.isOperation()) {
//               if (previousNode.right == null) {
//                   previousNode.right = new Node(nextToken);
//                   return previousNode;
//               } else {
//
//               }
//            }
//           return new Node(nextToken);
//        } else if (nextToken instanceof Operation) {
//            Node node = new Node(nextToken);
//            node.left = previousNode;
//            return process(next(), node);
//        } else if (nextToken instanceof OpeningBracket) {
//            previousNode.left = new Node(nextToken);
//            return process(next(), previousNode.left);
//        } else if (nextToken instanceof ClosingBracket) {
//            checkRight(previousNode);
//            return previousNode;
//        }
//    }
//
//    void checkLeft(Node node) {
//        if (node.left == null && !node.isAtom()) {
//            throw new IllegalStateException("Left must be present for non-atom node.");
//        }
//    }
//    void checkRight(Node node) {
//        if (node.right == null && !node.isAtom()) {
//            throw new IllegalStateException("Right must be present for non-atom node.");
//        }
//    }
//}
