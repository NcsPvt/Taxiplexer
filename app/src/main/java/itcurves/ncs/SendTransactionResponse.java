package itcurves.ncs;

import com.slimcd.library.transact.processtransaction.ProcessTransactionReply;
public interface SendTransactionResponse {
	  // call back method
    void sendResponseToActivity(ProcessTransactionReply reply);
}

