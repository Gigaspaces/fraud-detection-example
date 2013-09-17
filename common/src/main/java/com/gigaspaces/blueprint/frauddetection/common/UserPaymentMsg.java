package com.gigaspaces.blueprint.frauddetection.common;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/19/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserPaymentMsg implements Serializable {

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @SpaceRouting
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Payment payment;
    private User user;
    private Long userId;
    @SpaceId
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    private Long paymentId;


    public UserPaymentMsg(Payment payment, User user) {

        this.payment = payment;
        this.user = user;
        this.userId = user.getId();
        this.paymentId = payment.getId();

    }
    public UserPaymentMsg(){}
}
