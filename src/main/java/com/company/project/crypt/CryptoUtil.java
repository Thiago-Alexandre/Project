package com.company.project.crypt;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {

	private BasicTextEncryptor textEncryptor = null;

	public CryptoUtil(@Value("${key}") String key) {

		this.textEncryptor = new BasicTextEncryptor();
		this.textEncryptor.setPassword(key);
	}

	public String encrypt(String textToEncrypt) {

		return this.textEncryptor.encrypt(textToEncrypt);
	}

	public String decrypt(String encryptedText) {

		return this.textEncryptor.decrypt(encryptedText);
	}
}