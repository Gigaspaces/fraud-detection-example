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
