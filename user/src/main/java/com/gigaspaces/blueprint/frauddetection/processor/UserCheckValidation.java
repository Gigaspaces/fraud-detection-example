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

import com.gigaspaces.blueprint.frauddetection.common.*;
import com.gigaspaces.client.ChangeResult;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.client.ReadModifiers;
import com.gigaspaces.query.IdQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.notify.Notify;
import org.openspaces.events.notify.NotifyType;
import org.openspaces.events.polling.Polling;
import org.springframework.dao.DataAccessException;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/21/13
 * Time: 5:19 AM
 * To change this template use File | Settings | File Templates.
 */
@EventDriven
@Polling(gigaSpace = "gigaSpace")
@TransactionalEvent
public class UserCheckValidation {


    @EventTemplate
    UserPaymentMsg unprocessedData() {
        return new UserPaymentMsg();
    }

    @SpaceDataEvent
    public void validateUser(UserPaymentMsg event,GigaSpace gigaSpace) {

        try {
            //get the latest copy of user data
            User user = gigaSpace.readById(User.class, event.getUserId(),null,0,ReadModifiers.READ_COMMITTED);
            //get the latest copy of cards details
            Card cardTemplate = new Card();
            cardTemplate.setUserId(event.getUserId());
            Card[] cards = gigaSpace.readMultiple(cardTemplate,Integer.MAX_VALUE,ReadModifiers.READ_COMMITTED);
            //get all transaction for this user
            Payment paymentTemplate = new Payment();
            paymentTemplate.setUserId(event.getUserId());
            Payment[] payments = gigaSpace.readMultiple(paymentTemplate,Integer.MAX_VALUE,ReadModifiers.READ_COMMITTED);

            IdQuery<PaymentAuthorization> idQuery = new IdQuery<PaymentAuthorization>(PaymentAuthorization.class, event.getPaymentId());

            Boolean paymentValid = AuthorizeUserData(user, cards, payments);

            gigaSpace.change(idQuery, new ChangeSet().set("userCheck", paymentValid));

        } catch (DataAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    private Boolean AuthorizeUserData(User user, Card[] cards, Payment[] payments) {
                 //authorize all data and validate this payment
                 return true;
    }
}
