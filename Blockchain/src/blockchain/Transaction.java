package blockchain;

import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
	
	public String transactionID; //Hash of the Transaction
	public PublicKey sender; // Address of sender
	public PublicKey receiver; // Address of receiver
	public float value; 
	public byte[] signature; //Unique signature to authenticate values
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0; //Count of Transactions Generated
	
	public Transaction(PublicKey sender, PublicKey receiver, float value, ArrayList<TransactionInput> inputs)
	{
		
	}
	

}
