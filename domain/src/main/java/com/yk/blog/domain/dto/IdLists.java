package com.yk.blog.domain.dto;

import java.util.List;

/**
 * @author yikang
 * @date 2019/5/17
 */
public class IdLists {

    private List<String> userIds;
    private List<Integer> blogIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<Integer> getBlogIds() {
        return blogIds;
    }

    public void setBlogIds(List<Integer> blogIds) {
        this.blogIds = blogIds;
    }
}
