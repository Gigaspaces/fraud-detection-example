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
