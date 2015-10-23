package citmatel.cu.class_Pack;

import java.io.Serializable;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class License implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String client;
	private String entity;
	private String instalationID;
	private Date validDate;

	public License() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	
	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = 
	    new byte[] { '3', '6', 'R', '@', 'F', '1', 'P', '5', '3', 'C', 'R', '3', '7', 'K', '3', 'Y' };

	 public String encrypt(String valueToEnc) throws Exception {
	    Key key = generateKey();
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    c.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encValue = c.doFinal(valueToEnc.getBytes());
	    String encryptedValue = new BASE64Encoder().encode(encValue);
	    return encryptedValue;
	}

	public String decrypt(String encryptedValue) throws Exception {
	    Key key = generateKey();
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    c.init(Cipher.DECRYPT_MODE, key);
	    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
	    byte[] decValue = c.doFinal(decordedValue);
	    String decryptedValue = new String(decValue);
	    return decryptedValue;
	}

	private static Key generateKey() throws Exception {
	    Key key = new SecretKeySpec(keyValue, ALGORITHM);
	    return key;
	}

	/**
	 * @param instalationID the instalationID to set
	 */
	public void setInstalationID(String instalationID) throws Exception {
		this.instalationID = encrypt(instalationID);
	}

	/**
	 * @return the instalationID
	 */
	public String getInstalationID() throws Exception {
		return decrypt(instalationID);
	}
	
	

}
