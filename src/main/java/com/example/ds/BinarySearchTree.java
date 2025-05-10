package com.example.ds;

import com.example.entity.Agents;

public class BinarySearchTree {
    private class Node {
        Agents agent;
        Node left, right;

        Node(Agents agent) {
            this.agent = agent;
            left = right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(Agents agent) {
        root = insertRec(root, agent);
    }

    private Node insertRec(Node root, Agents agent) {
        if (root == null) {
            root = new Node(agent);
            return root;
        }
        if (agent.compareTo(root.agent) < 0) {
            root.left = insertRec(root.left, agent);
        } else if (agent.compareTo(root.agent) > 0) {
            root.right = insertRec(root.right, agent);
        }
        return root;
    }

    public Agents search(Long id) {
        return searchRec(root, id);
    }

    private Agents searchRec(Node root, Long id) {
        if (root == null || root.agent.getId().equals(id)) {
            return root != null ? root.agent : null;
        }
        if (id < root.agent.getId()) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }

    public void inOrderTraversal(java.util.List<Agents> result) {
        inOrderRec(root, result);
    }

    private void inOrderRec(Node root, java.util.List<Agents> result) {
        if (root != null) {
            inOrderRec(root.left, result);
            result.add(root.agent);
            inOrderRec(root.right, result);
        }
    }

    public void delete(Long id) {
        root = deleteRec(root, id);
    }

    private Node deleteRec(Node root, Long id) {
        if (root == null) {
            return root;
        }
        if (id < root.agent.getId()) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.agent.getId()) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.agent = minValue(root.right);
            root.right = deleteRec(root.right, root.agent.getId());
        }
        return root;
    }

    private Agents minValue(Node root) {
        Agents minv = root.agent;
        while (root.left != null) {
            minv = root.left.agent;
            root = root.left;
        }
        return minv;
    }
}