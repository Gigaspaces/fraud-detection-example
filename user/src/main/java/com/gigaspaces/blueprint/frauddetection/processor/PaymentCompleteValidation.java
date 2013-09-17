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
package com.gigaspaces.blueprint.frauddetection.processor;

import com.gigaspaces.blueprint.frauddetection.common.Payment;
import com.gigaspaces.blueprint.frauddetection.common.PaymentAuthorization;
import com.j_spaces.core.client.ReadModifiers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/22/13
 * Time: 5:41 AM
 * To change this template use File | Settings | File Templates.
 */

@Polling (gigaSpace = "gigaSpace")
@EventDriven
@TransactionalEvent
public class PaymentCompleteValidation {

    private Log log = LogFactory.getLog(PaymentCompleteValidation.class);

    @EventTemplate
    public PaymentAuthorization getNewPayment()
    {
        return new PaymentAuthorization(null,true,true, PaymentAuthorization.PaymentAuthorizationStatus.New);
    }


    @SpaceDataEvent
    public void completePaymentValidation(PaymentAuthorization paymentAuthorization,GigaSpace gigaSpace)
    {

        //getting the proper payment
        Payment payment =  gigaSpace.readById(Payment.class,paymentAuthorization.getPaymentId(),null, ReadModifiers.EXCLUSIVE_READ_LOCK);

        payment.setPaymentStatus(Payment.PaymentStatus.Closed);

        gigaSpace.write(payment);
        paymentAuthorization.setPaymentAuthorizationStatus(PaymentAuthorization.PaymentAuthorizationStatus.Done);

        log.info("Completed authorization for " + payment.getId());

    }



}
