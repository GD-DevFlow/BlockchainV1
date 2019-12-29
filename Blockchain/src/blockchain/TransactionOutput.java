package blockchain;
import java.security.PublicKey;

public class TransactionOutput {
	
	public String id;
	public PublicKey receiver; // Owner of new coins
	public float value; //Amount of coins owned
	public String parentTransactionID; // ID of transaction this output was created in
	
	public TransactionOutput(PublicKey receiver, float value, String parentTransactionID)
	{
		this.receiver = receiver;
		this.value = value;
		this.parentTransactionID = parentTransactionID;
		this.id = ApplySha.applySha256(ApplySha.getStringFromKey(receiver) + Float.toString(value) + parentTransactionID);
	}
	
	// Check Belonging of coin
	
	public boolean isMine(PublicKey publicKey)
	{
		return (publicKey == receiver);
	}

}
