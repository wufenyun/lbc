/**
 * 
 */
package com.lbc.wrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.lbc.wrap.query.Query;
import com.lbc.support.AssertUtil;
import com.lbc.support.PropertyUtil;
import com.lbc.wrap.tree.TreeNode;
import com.lbc.wrap.tree.Treeable;

/**
 * @see  QueryingCollection
 * 
 * @author wufenyun
 * @param <V>
 * @date 2018年3月3日 上午10:53:32
 */
public class SimpleQueryingCollection<V> implements QueryingCollection<V> {

	private List<V> data;

	public SimpleQueryingCollection() {

    }

	public SimpleQueryingCollection(List<V> data) {
        this.data = data;
    }

	@Override
	public QueryingCollection<V> wrap(List<V> v) {
		this.data = v;
		return this;
	}

    @Override
    public List<V> query(Query query) {
        if(null == query) {
            return data.stream().collect(Collectors.toList());
        }
        return data.stream().filter((v)->query.predict(v)).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K> Map<K, V> asMap(String keyFieldName) {
        AssertUtil.notBlank(keyFieldName, "属性字段名不能为空");
        return (Map<K, V>) data.stream().collect(Collectors.toConcurrentMap(x -> {
            return PropertyUtil.getFieldValue(x, keyFieldName);
        }, y -> y));
    }

    @Override
    public List<V> all() {
        return data;
    }

    @Override
    public long size() {
        return data.size();
    }

    @Override
    public List<TreeNode<V>> generateTrees() {
        List<TreeNode<V>> rootNodes = new ArrayList<>();
        Map<Object,V> entityMap = new ConcurrentHashMap<>();
        Map<Object,TreeNode> nodeMap = new ConcurrentHashMap<>();

        data.forEach(v -> {
            if(!(v instanceof Treeable)) {
                throw new IllegalArgumentException("对象要生成树需要实现Treeable接口");
            }
            Treeable node = (Treeable) v;
            Object id = node.returnUniqueId();
            entityMap.put(id,v);
        });

        data.forEach(v -> {
            loadParent2Extend((Treeable) v,entityMap,nodeMap,rootNodes);
        });
        return rootNodes;
    }

    /**
     * 加载当前节点的父节点并将当前节点设置为其子节点
     *
     * @param currentEntity 当前节点
     * @param entityMap
     * @param nodeMap
     * @param rootNodes
     */
    private void loadParent2Extend(Treeable currentEntity,Map<Object,V> entityMap,Map<Object,TreeNode> nodeMap,List<TreeNode<V>> rootNodes) {
        if(null != nodeMap.get(currentEntity.returnUniqueId())) {
            return;
        }

        //将当前树节点加入到树节点map，用以记录已经加载过此节点
        TreeNode currentNode = new TreeNode();
        currentNode.setData(currentEntity);
        nodeMap.put(currentEntity.returnUniqueId(),currentNode);

        //递归加载父节点,知道父节点到达根节点
        Object parentId = currentEntity.returnUniqueParentId();
        if(parentId==null || null == entityMap.get(parentId)) {
            rootNodes.add(currentNode);
            return;
        } else {
            //如果nodeMap中已经包含父节点，说明已经加载过，直接继承后返回
            TreeNode parentNode = nodeMap.get(parentId);
            if(null == parentNode) {
                Treeable parentEntity = (Treeable) entityMap.get(parentId);
                loadParent2Extend(parentEntity,entityMap,nodeMap,rootNodes);

                parentNode = nodeMap.get(parentId);
            }

            if(null == parentNode.getChildren()) {
                List<TreeNode> children = new ArrayList<>();
                parentNode.setChildren(children);
            }
            parentNode.getChildren().add(currentNode);
        }


    }

}
