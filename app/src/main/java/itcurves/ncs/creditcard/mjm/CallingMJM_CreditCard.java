package itcurves.ncs.creditcard.mjm;

import itcurves.ncs.AVL_Service;
import itcurves.ncs.IMessageListener;
import android.os.AsyncTask;

public class CallingMJM_CreditCard extends AsyncTask<Object, Integer, MJM_CreditCardResponse> {

	private final String msg;
	private String response;

	public CallingMJM_CreditCard(String msgToShow) {
		super();
		this.msg = msgToShow;
	}

	@Override
	protected void onPreExecute() {

		for (IMessageListener list : AVL_Service.msg_listeners.values())
			list.showProgressDialog(msg);

	}// onPreExecute

	@Override
	protected MJM_CreditCardResponse doInBackground(Object... mjmTransactionType) {

		try {
			if (mjmTransactionType[0] instanceof MJM_Authorize) {
				MJM_Authorize a = (MJM_Authorize) mjmTransactionType[0];
				response = a.SendAuthorizeRequest();
			} else if (mjmTransactionType[0] instanceof MJM_PostAuthorize) {
				MJM_PostAuthorize pa = (MJM_PostAuthorize) mjmTransactionType[0];
				response = pa.SendPostAuthorizeRequest();
			} else if (mjmTransactionType[0] instanceof MJM_Sale) {
				MJM_Sale s = (MJM_Sale) mjmTransactionType[0];
				response = s.SendSaleRequest();
			} else if (mjmTransactionType[0] instanceof MJM_Return) {
				MJM_Return r = (MJM_Return) mjmTransactionType[0];
				response = r.SendReturnRequest();
			} else if (mjmTransactionType[0] instanceof MJM_VoidSale) {
				MJM_VoidSale vs = (MJM_VoidSale) mjmTransactionType[0];
				response = vs.SendVoidSaleRequest();
			}
			return new MJM_CreditCardResponse(response);

		} catch (Exception e) {
			return null;
		}
	}// doInBackground

	@Override
	protected void onPostExecute(MJM_CreditCardResponse result) {
		try {

			for (IMessageListener list : AVL_Service.msg_listeners.values())
				list.hideProgressDialog();
			this.finalize();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}// onPostExecute

}
