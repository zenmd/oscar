package com.quatro.model.signaturePad;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import org.hibernate.Hibernate;

public class TopazValue implements Serializable{
	  String moduleName;
	  Integer recordId;
	  byte[] signature;
	  String providerNo;
	  Calendar lastUpdateDate;
	  
	  public byte[] getSignature() {
		return signature;
	  }
	  
	  public void setSignature(byte[] signature) {
		this.signature = signature;
	  }

	  public void setSignatureBlob(Blob signatureBlob) {
		  this.signature = this.toByteArray(signatureBlob);
	  } 

	  // Don't invoke this.  Used by Hibernate only. 
	  public Blob getSignatureBlob() {
		  return Hibernate.createBlob(this.signature); 
	  }
	  
	  private byte[] toByteArray(Blob fromBlob){
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  try {
			  return toByteArrayImpl(fromBlob, baos);
		  }catch(SQLException e){
			  throw new RuntimeException(e);
		  }catch (IOException e){
			  throw new RuntimeException(e);
		  }finally{
			if (baos != null) {
			  try {
				  baos.close();
			  } catch(IOException ex) {
				  
			  }
			}
		  }
	  }
	  
	  private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)  
	       throws SQLException, IOException {
	     byte[] buf = new byte[4000];
	     InputStream is = fromBlob.getBinaryStream();
	     try {
	    	 for (;;) {
	    	   int dataSize = is.read(buf);
	    	   if (dataSize == -1) break;
	    	   baos.write(buf, 0, dataSize);
	    	 }
	     } finally {
	    	if (is != null){
	    	   try {
	    		   is.close();
	    	   }catch(IOException ex) {}
	    	}
	     }

	     return baos.toByteArray();
	  }

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}
}
