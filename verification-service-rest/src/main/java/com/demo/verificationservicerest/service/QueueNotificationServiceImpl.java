package com.demo.verificationservicerest.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.demo.verificationservicerest.service.model.VerificationStatusModel;

import lombok.extern.slf4j.Slf4j;

@Service
@EnableBinding({VerficationStatusOutbound.class})
@Slf4j
public class QueueNotificationServiceImpl implements NotificationService {
	private final VerficationStatusOutbound outBoundChannel;

	public QueueNotificationServiceImpl(VerficationStatusOutbound outBoundChannel) {
		super();
		this.outBoundChannel = outBoundChannel;
	}

	@Override
	public void notifyStatus(VerificationStatusModel verificationStatus) {
		outBoundChannel.output().send(message(verificationStatus));
		log.info("Message Sent: [{}]", verificationStatus);

	}
	
	private final <T> Message<T> message(T val) {
	    return MessageBuilder.withPayload(val).build();
	}

}
