package com.realestate.agentsystem.util.datastructure;

import java.util.ArrayList;
import java.util.List;


// BinarySearchTree.java
public class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public void insert(T data) {
        root = insertRecursive(root, data);
    }

    private Node<T> insertRecursive(Node<T> current, T data) {
        if (current == null) {
            return new Node<>(data);
        }

        if (data.compareTo(current.data) < 0) {
            current.left = insertRecursive(current.left, data);
        } else if (data.compareTo(current.data) > 0) {
            current.right = insertRecursive(current.right, data);
        }

        return current;
    }

    public T find(T data) {
        Node<T> result = findRecursive(root, data);
        return result == null ? null : result.data;
    }

    private Node<T> findRecursive(Node<T> current, T data) {
        if (current == null || data.compareTo(current.data) == 0) {
            return current;
        }

        if (data.compareTo(current.data) < 0) {
            return findRecursive(current.left, data);
        }

        return findRecursive(current.right, data);
    }

    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node<T> node, List<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.data);
            inOrderTraversal(node.right, result);
        }
    }

    // Additional BST operations like delete, update, etc.
}
