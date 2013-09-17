package com.gigaspaces.blueprint.frauddetection.common;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/19/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Card {

    private Long id;

    public Long getUserId() {
        return userId;
    }
    @SpaceRouting
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;


    public void setId(Long id) {
        this.id = id;
    }
    @SpaceId
    public Long getId() {
        return id;
    }
}
