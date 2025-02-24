package com.tujia.myssm.solution.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author: songlinl
 * @create: 2023/7/2 13:30
 */
public class T662 {

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */
    public int widthOfBinaryTree(TreeNode root) {
        List<Integer> leftVal = new ArrayList<>();
        List<Integer> rightVal = new ArrayList<>();
        leftVal.add(0);
        if (root.left != null) {
            leftVal.add(1);
        }
        rightVal.add(0);
        if (root.right != null) {
            rightVal.add(1);
        }
        findLeft(root.left, leftVal, 1);
        findR(root.right, rightVal, 1);
        int len = Math.min(leftVal.size(), rightVal.size());
        int max = 0;
        for (int i = 1; i < len; i++) {
            int tem = rightVal.get(i) + leftVal.get(i);
            if (tem > max) {
                max = tem;
            }
        }
        return max;
    }

    public void find(TreeNode root, List<Integer> val, int deep, boolean isLeft) {
        if (root == null) {
            return;
        }
        TreeNode next = isLeft ? root.left : root.right;
        if (next != null) {
            val.add(val.get(deep) * 2);
            find(next, val, deep + 1, isLeft);
        }

        TreeNode next2 = isLeft ? root.right : root.left;
        if (next2 != null) {
            val.add(val.get(deep) * 2 - 1);
            find(next2, val, deep + 1, isLeft);
        }
    }

    public void findR(TreeNode root, List<Integer> rightVal, int deep) {
        if (root == null) {
            return;
        }
        if (root.right != null) {
            rightVal.add(rightVal.get(deep) * 2);
            findLeft(root.right, rightVal, deep + 1);
            return;
        }
        if (root.left != null) {
            rightVal.add(rightVal.get(deep) * 2 - 1);
            findLeft(root.left, rightVal, deep + 1);
        }
    }

    public void findLeft(TreeNode root, List<Integer> leftVal, int deep) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            leftVal.add(leftVal.get(deep) * 2);
            findLeft(root.left, leftVal, deep + 1);
            return;
        }
        if (root.right != null) {
            leftVal.add(leftVal.get(deep) * 2 - 1);
            findLeft(root.right, leftVal, deep + 1);
        }
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

