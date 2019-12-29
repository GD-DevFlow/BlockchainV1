package blockchain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


// https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce



public class Block {

    private String hash;
    private String previousHash;
    private String data;
    private String merkleRoot;
    private long timeStamp;
    private int nonce;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();

/** This constructor is used to create a new Block
 * @param data: Actual data that is contained in the block
 * @param previousHash: Hash of block i-1 (where i is the current block)
 */
    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
        
    }

    // Increase Nonce value until reached
    public void mineBlock(int difficulty) {
    	merkleRoot = ApplySha.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * 0
        while (!hash.substring(0, difficulty).equals(target)) 
        {
        	nonce++;
            hash = calculateHash();
        }
        System.out.println("Block has been mined!!  : " + hash);
    }
    
    public boolean addTransaction(Transaction transaction)
    {
    	//Process Transaction and check if valid, unless block is genesis then ignore
    	if(transaction == null)
    		return false;
    	if(previousHash != "0")
    	{
    		if((transaction.processTransaction() != true))
    		{
    			System.out.println("Transaction failed to process. Discarded");
    			return false;
    		}
    	}
    	
    	transactions.add(transaction);
    	System.out.println("Transaction succesfully added to Block");
    	return true;
    }

    // Calculate Hash based on block contents
    public String calculateHash() {
        String calculatedhash = ApplySha.applySha256(previousHash + Long.toString(timeStamp) + data + Integer.toString(nonce) + merkleRoot);
        return calculatedhash;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public void setData(String data) {
        this.data = data;
    }
}
