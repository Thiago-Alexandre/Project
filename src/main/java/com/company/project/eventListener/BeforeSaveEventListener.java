package com.company.project.eventListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

import com.company.project.crypt.CryptoUtil;
import com.company.project.model.Client;

public class BeforeSaveEventListener extends AbstractMongoEventListener<Object> {

	Logger log = LoggerFactory.getLogger(BeforeSaveEventListener.class);

	@Autowired
	private CryptoUtil cryptoUtil;

	@Override
	public void onBeforeSave(BeforeSaveEvent<Object> event) {

		Document eventObject = event.getDocument();
		List<String> keysNotToEncrypt = new LinkedList<String>(Arrays.asList("_class", "_id"));
		if(Client.class.toString().contains(eventObject.get("_class").toString())) {
			keysNotToEncrypt.add("age");
			keysNotToEncrypt.add("city");
			keysNotToEncrypt.add("state");
		}
		for (String key : eventObject.keySet()) {
			if (!keysNotToEncrypt.contains(key)) {
				eventObject.put(key, this.cryptoUtil.encrypt(eventObject.get(key).toString()));
			}
		}
		super.onBeforeSave(event);
	}
}