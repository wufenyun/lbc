package com.lbc.wrap.tree;

import java.util.List;

/**
 * @description: 树节点的抽象
 * @author: wufenyun
 * @date: 2018-06-26 16
 **/
public class TreeNode<T> {

    /**
     * 当前节点
     */
    private T data;

    /**
     * 子节点
     */
    private List<TreeNode> children;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}