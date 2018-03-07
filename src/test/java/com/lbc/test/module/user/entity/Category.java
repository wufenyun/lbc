package com.lbc.test.module.user.entity;

import java.util.List;
import java.util.Map;

/**
 * @Author xutaoyang
 * @Date 2017/6/13
 */
public class Category {

    private String id;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 父类目id
     */
    private Integer parentId;

    /**
     * 0关闭 1开启
     */
    private Integer status;

    /**
     * 排序，降序
     */
    private Integer sort;

    /**
     * 版本号
     */
    private Integer versionId;

    /**
     * 层级
     */
    private Integer level;
    
    /**
     * 类目名 
     */
    private Map<String,String> categoryNameMap;
    
	/**
     * 允许入驻平台 
     */
    private List<Integer> isJoin;
    
    /**
     * 允许发布服务 
     */
    private List<Integer> isPublishService;
    
    /**
     * 服务类型，个人服务/企业服务
     */
    private Integer serviceType;
    
	public List<Integer> getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(List<Integer> isJoin) {
		this.isJoin = isJoin;
	}

	public List<Integer> getIsPublishService() {
		return isPublishService;
	}

	public void setIsPublishService(List<Integer> isPublishService) {
		this.isPublishService = isPublishService;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

	public Map<String,String> getCategoryNameMap() {
		return categoryNameMap;
	}

	public void setCategoryNameMap(Map<String,String> categoryNameMap) {
		this.categoryNameMap = categoryNameMap;
	}
}
