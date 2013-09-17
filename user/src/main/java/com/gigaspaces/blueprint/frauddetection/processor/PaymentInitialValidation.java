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
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.springframework.dao.DataAccessException;


/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/19/13
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Polling(gigaSpace = "gigaSpace")
@EventDriven
@TransactionalEvent
public class PaymentInitialValidation {

    @EventTemplate
    public SQLQuery<Payment> getNewPayment()
    {
       SQLQuery<Payment> query =   new SQLQuery<Payment>(Payment.class," paymentStatus = ? ");
        query.setParameter(1, Payment.PaymentStatus.New);
       return query;
    }


    @SpaceDataEvent
    public Payment validatePayment(Payment payment,GigaSpace gigaSpace)
    {

        //GET current user for this payment
        User user  = gigaSpace.readById(User.class, payment.getUserId());
        if ( user == null )
            user=  createUser(payment.getUserId(),gigaSpace);
        Card card = gigaSpace.readById(Card.class,payment.getCardId());
        if ( card == null )
            card = createCard(payment,gigaSpace);

        //validate basic data of payment/user/card
        if ( basicPaymentValidation(payment,user,card))
        {

            try {
                //here we will fork the two fraud detection processes, by card info and by merchant
                UserPaymentMsg userPaymentMsg=   new UserPaymentMsg(payment,user);
                VendorPaymentMsg vendorPaymentMsg =  new VendorPaymentMsg(payment,user);
                //this object will match the completion of the validation process
                PaymentAuthorization paymentAuthorization = new PaymentAuthorization(payment.getId(),false,false, PaymentAuthorization.PaymentAuthorizationStatus.New);
                gigaSpace.write(userPaymentMsg);
                gigaSpace.write(vendorPaymentMsg);
                gigaSpace.write(paymentAuthorization);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            payment.setPaymentStatus(Payment.PaymentStatus.Processing);
        }
        else
        {
            payment.setPaymentStatus(Payment.PaymentStatus.SuspectedFraud);
        }


        payment.setPaymentStatus(Payment.PaymentStatus.Closed);

        return payment;
    }

    private boolean basicPaymentValidation(Payment payment, User user, Card card) {

        //To change body of created methods use File | Settings | File Templates.

        return true;
    }

    private Card createCard(Payment payment, GigaSpace gigaSpace) {

        Card card = new Card();
        card.setId(payment.getCardId());
        card.setUserId(payment.getUserId());
        gigaSpace.write(card);
        return card;

    }

    private User createUser(Long userId, GigaSpace gigaSpace) {

        User user = new User();
        user.setId(userId);
        user.setName("UserName " + userId);
        gigaSpace.write(user);
        return user;
    }


}
