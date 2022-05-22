package com.tujia.myssm.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.tujia.myssm.api.model.TreeNode;

/**
 *
 * @author: songlinl
 * @create: 2022/05/10 12:02
 */
public class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> treeNodeStack = new Stack<>();
        Stack<Integer> result = new Stack<>();
        treeNodeStack.push(root);
        stack(treeNodeStack, result);
        List<Integer> res = new ArrayList<>();
        while (!result.isEmpty()) {
            res.add(result.pop());
        }
        return res;
    }

    public void stack(Stack<TreeNode> treeNodeStack, Stack<Integer> result) {
        while (!treeNodeStack.isEmpty()) {
            TreeNode head = treeNodeStack.pop();
            if (head == null) {
                continue;
            }
            result.push(head.val);
            treeNodeStack.push(head.left);
            treeNodeStack.push(head.right);
        }
    }
}
