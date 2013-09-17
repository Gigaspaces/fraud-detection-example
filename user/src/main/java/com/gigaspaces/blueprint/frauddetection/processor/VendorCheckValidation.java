package com.gigaspaces.blueprint.frauddetection.processor;

import com.gigaspaces.async.AsyncFuture;
import com.gigaspaces.blueprint.frauddetection.Task.VendorValidationTask;
import com.gigaspaces.blueprint.frauddetection.common.PaymentAuthorization;
import com.gigaspaces.blueprint.frauddetection.common.VendorPaymentMsg;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;

/**
 * Created with IntelliJ IDEA.
 * User: guyl
 * Date: 2/21/13
 * Time: 5:29 AM
 * To change this template use File | Settings | File Templates.
 */
@Polling(gigaSpace = "gigaSpace")
@EventDriven
@TransactionalEvent
public class VendorCheckValidation {

    @GigaSpaceContext(name="vendorGigaSpace")
    GigaSpace vendorGigaSpace;

    @EventTemplate
    VendorPaymentMsg unprocessedData() {
        return new VendorPaymentMsg();
    }

    @SpaceDataEvent
    public void validateVendor(VendorPaymentMsg vendorPaymentMsg,GigaSpace gigaSpace)
    {
        //we will use  Task to validate the vendor
        AsyncFuture<Boolean> future = vendorGigaSpace.execute(new VendorValidationTask(vendorPaymentMsg),vendorPaymentMsg.getMerchant());

        try {
            IdQuery<PaymentAuthorization> idQuery = new IdQuery<PaymentAuthorization>(PaymentAuthorization.class, vendorPaymentMsg.getPaymentId());
            //is this is true then we can confirm the transaction has passed the vendor validation
            if (future.get())
                gigaSpace.change(idQuery, new ChangeSet().set("vendorCheck", true));
            else
                gigaSpace.change(idQuery, new ChangeSet().set("vendorCheck", false));

        } catch (Exception e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


}
