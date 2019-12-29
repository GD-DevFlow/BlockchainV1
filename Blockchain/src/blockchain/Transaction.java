package blockchain;

import java.security.PublicKey;
import java.security.*;
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
		this.sender = sender;
		this.receiver = receiver;
		this.value = value;
		this.inputs = inputs;
	}
	
	public String calculateTxHash()
	{
		sequence++;
		return ApplySha.applySha256(ApplySha.getStringFromKey(sender) + ApplySha.getStringFromKey(receiver) + Float.toString(value) + sequence);
				
	} 
	
	public void generateSignature(PrivateKey privateKey)
	{
		String data = ApplySha.getStringFromKey(sender) + ApplySha.getStringFromKey(receiver) + Float.toString(value);
		signature = ApplySha.applyECDSASig(privateKey, data);
	
	}
	
	public boolean verifySignature()
	{
		String data = ApplySha.getStringFromKey(sender) + ApplySha.getStringFromKey(receiver) + Float.toString(value);
		return ApplySha.verifyECDSASig(sender, signature, data);
	}
	
	public boolean processTransaction()
	{
		if(verifySignature() == false)
		{
			System.out.println("# Transaction Signature failed to Verify");
			return false;
		}
		
		// Gather transaction inputs (Unspent)
		for(TransactionInput i: inputs)
		{
			i.UTXO = BlockchainMain.UTXOs.get(i.transactionOutputID);
		}
	
		// Checking if transaction is valid
		if(getInputsValue() < BlockchainMain.minimumTransaction)
		{
			System.out.println("# Transaction Inputs too small: " + getInputsValue());
			return false;
		}
		
		//Generating array of transaction outputs
		float leftOver = getInputsValue() - value; 
		transactionID = calculateTxHash();
		outputs.add(new TransactionOutput(this.receiver, value, transactionID)); //Send value to receiver
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionID)); //Send leftover "change" back to sender
		
		//add outputs to Unspent List
		for(TransactionOutput o: outputs)
		{
			BlockchainMain.UTXOs.put(o.id, o);
		}
		
		//Remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i: inputs)
		{
			if(i.UTXO == null)
				continue; // If Transaction cannot be found then skip
			BlockchainMain.UTXOs.remove(i.UTXO.id);
		}
		return true;
	}
	
	// Returns sum of inputs (UTXOs) values
	public float getInputsValue()
	{
		float total = 0;
		for(TransactionInput i: inputs)
		{
			if(i.UTXO == null) continue; // Skip if cant be found
			total += i.UTXO.value;
		}
		return total;
	}
	
	//Returns sum of outputs
	public float getOutputsValue()
	{
		float total = 0;
		for(TransactionOutput o: outputs)
		{
			total += o.value;
		}
		return total;
	}
	

}
