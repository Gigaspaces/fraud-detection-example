package com.gigaspaces.blueprint.frauddetection.common;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 1/20/13
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class PaymentAuthorization {


    private PaymentAuthorizationStatus paymentAuthorizationStatus;
    private Boolean vendorCheck;
    private Boolean userCheck;
    private Long paymentId;

    public PaymentAuthorization(){}

    public PaymentAuthorization(Long id, boolean vendorCheck, boolean userCheck, PaymentAuthorizationStatus status) {

        this.paymentId = id;
        this.vendorCheck = vendorCheck;
        this.paymentAuthorizationStatus = status;
        this.userCheck = userCheck;
    }

    @SpaceId
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Boolean getUserCheck() {
        return userCheck;
    }

    public void setUserCheck(Boolean userCheck) {
        this.userCheck = userCheck;
    }

    public Boolean getVendorCheck() {
        return vendorCheck;
    }

    public void setVendorCheck(Boolean vendorCheck) {
        this.vendorCheck = vendorCheck;
    }

    public PaymentAuthorizationStatus getPaymentAuthorizationStatus() {
        return paymentAuthorizationStatus;
    }

    public void setPaymentAuthorizationStatus(PaymentAuthorizationStatus paymentAuthorizationStatus) {
        this.paymentAuthorizationStatus = paymentAuthorizationStatus;
    }


    public enum PaymentAuthorizationStatus {
        New,
        Done
    }

}
