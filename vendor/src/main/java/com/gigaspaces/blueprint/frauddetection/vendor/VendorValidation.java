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
package com.gigaspaces.blueprint.frauddetection.vendor;

import com.gigaspaces.blueprint.frauddetection.common.PaymentAuthorization;
import com.gigaspaces.blueprint.frauddetection.common.VendorPaymentMsg;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.core.space.mode.PostPrimary;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.SimpleNotifyContainerConfigurer;
import org.openspaces.events.notify.SimpleNotifyEventListenerContainer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/21/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendorValidation implements DisposableBean,InitializingBean {


    private SimpleNotifyEventListenerContainer notifyEventListenerContainer;
    private GigaSpace gigaSpace;
    private Log log = LogFactory.getLog(VendorValidation.class);


    @PostPrimary
    public void startNotifyValidation()
    {

        log.error(" Start Gigaspaces proxy to user cluster ");
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("jini://*/*/user");
        gigaSpace = new GigaSpaceConfigurer(urlSpaceConfigurer.space()).gigaSpace();



        log.error(" Start notify container ");
        notifyEventListenerContainer = new SimpleNotifyContainerConfigurer(gigaSpace)
                .template(new VendorPaymentMsg())
                .eventListenerAnnotation(new Object() {
                    @SpaceDataEvent
                    public void eventHappened(VendorPaymentMsg event) {


                        log.error("Event is started " + event.getPaymentId());
                        IdQuery<PaymentAuthorization> idQuery = new IdQuery<PaymentAuthorization>(PaymentAuthorization.class, event.getPaymentId());
                        gigaSpace.change(idQuery, new ChangeSet().set("vendorCheck", true));


                    }
                }).notifyContainer();

        notifyEventListenerContainer.start();


    }


    @Override
    public void destroy() throws Exception {

        log.error(" destroy proxy  ");
        notifyEventListenerContainer.destroy();

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        log.error("This is after property set ");

    }
}
