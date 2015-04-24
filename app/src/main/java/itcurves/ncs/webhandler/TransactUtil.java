/* 
 * ===========================================================================
 * File Name TransactUtil.java
 * 
 * Created on Oct 24, 2013
 *
 * This code contains copyright information which is the proprietary property
 * of SlimCD . No part of this code may be reproduced, stored or transmitted
 * in any form without the prior written permission of SlimCD.
 *
 * Copyright (C) SlimCD. 2013
 * All rights reserved.
 *
 * Modification history:
 * $Log: TransactUtil.java,v $
 * ===========================================================================
 */
package itcurves.ncs.webhandler;

import itcurves.ncs.SendTransactionResponse;

import com.slimcd.library.transact.callback.ProcessTransactionCallback;
import com.slimcd.library.transact.processtransaction.ProcessTransactionReply;
import com.slimcd.library.transact.processtransaction.ProcessTransactionRequest;
import com.slimcd.library.transact.service.TransactProcessTransaction;

public class TransactUtil {

	// Transact

	/**
	 * 
	 * @author niveditat
	 * @date Oct 28, 2013
	 * @return void
	 * @param request
	 * @param response
	 * @exception
	 * @Description: To get the web service response.
	 */
	public static void getProcessTransaction(ProcessTransactionRequest request, final SendTransactionResponse response) {

		new TransactProcessTransaction().processTransaction(request, new ProcessTransactionCallback() {

			@Override
			public void getProcessTransactionReply(ProcessTransactionReply reply) {

				response.sendResponseToActivity(reply);

			}
		});

	}

}
