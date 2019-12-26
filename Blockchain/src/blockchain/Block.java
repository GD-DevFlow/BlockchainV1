package blockchain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


// https://www.baeldung.com/java-blockchain

public class Block {

    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

/** This constructor is used to create a new Block
 * @param data: Actual data that is contained in the block
 * @param previousHash: Hash of block i-1 (where i is the current block)
 */
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
        
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * 0
        while (!hash.substring(0, difficulty).equals(target)) 
        {
        	nonce++;
            hash = calculateHash();
        }
        System.out.println("Block has been mined!!  : " + hash);
    }

    public String calculateHash() {
        String calculatedhash = ApplySha.applySha256(previousHash + Long.toString(timeStamp) + data + Integer.toString(nonce));
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
