/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
