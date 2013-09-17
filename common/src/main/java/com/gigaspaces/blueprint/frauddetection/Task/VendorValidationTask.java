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
package com.gigaspaces.blueprint.frauddetection.Task;

import com.gigaspaces.blueprint.frauddetection.common.VendorPaymentMsg;
import org.openspaces.core.executor.Task;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 2/21/13
 * Time: 5:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class VendorValidationTask implements Task<Boolean> {


    private final VendorPaymentMsg vendorPaymentMsg;

    public VendorValidationTask(VendorPaymentMsg vendorPaymentMsg) {
        //To change body of created methods use File | Settings | File Templates.
        this.vendorPaymentMsg = vendorPaymentMsg;
    }

    @Override
    public Boolean execute() throws Exception {

        return validatePayment(vendorPaymentMsg);

    }

    /**
     * This method will validate user and merchant data and return true if this purchase is validated
     * @param vendorPaymentMsg
     * @return validate true or false
     */
    private Boolean validatePayment(VendorPaymentMsg vendorPaymentMsg) {

        //To change body of created methods use File | Settings | File Templates.
        if ( vendorPaymentMsg != null )
            return true;

        return false;
    }
}
