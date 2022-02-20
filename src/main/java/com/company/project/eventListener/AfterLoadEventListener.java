package com.company.project.eventListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;

import com.company.project.crypt.CryptoUtil;
import com.company.project.model.Client;

public class AfterLoadEventListener extends AbstractMongoEventListener<Object> {

	Logger log = LoggerFactory.getLogger(AfterLoadEventListener.class);

	@Autowired
	private CryptoUtil cryptoUtil;

	@Override
	public void onAfterLoad(AfterLoadEvent<Object> event) {

		Document eventObject = event.getDocument();
		List<String> keysNotToDecrypt = new LinkedList<String>(Arrays.asList("_class", "_id"));
		if(Client.class.toString().contains(eventObject.get("_class").toString())) {
			keysNotToDecrypt.add("age");
			keysNotToDecrypt.add("city");
			keysNotToDecrypt.add("state");
		}
		for (String key : eventObject.keySet()) {
			if (!keysNotToDecrypt.contains(key)) {
				eventObject.put(key, this.cryptoUtil.decrypt(eventObject.get(key).toString()));
			}
		}
		super.onAfterLoad(event);
	}
}