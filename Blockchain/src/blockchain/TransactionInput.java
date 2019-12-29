package blockchain;

public class TransactionInput {
	
	public String transactionOutputID; //Reference tp TransactionOutputs -> TxID
	public TransactionOutput UTXO; //Contains the UNSPENT transaction outputs
	
	public TransactionInput(String transactionOutputID)
	{
		this.transactionOutputID = transactionOutputID;
	}


}

