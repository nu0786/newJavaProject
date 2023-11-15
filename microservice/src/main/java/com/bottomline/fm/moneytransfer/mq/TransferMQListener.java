package com.bottomline.fm.moneytransfer.mq;

import com.bottomline.fm.moneytransfer.dto.TransferRequest;
import com.bottomline.fm.moneytransfer.service.spi.TransferService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;

public class TransferMQListener {
    private static Logger logger = LoggerFactory.getLogger(TransferMQListener.class);
    private ObjectMapper objectMapper;
    private TransferService transferService;

    public TransferMQListener(TransferService transferService) {
        objectMapper = new ObjectMapper();
        this.transferService = transferService;
    }

    @JmsListener(destination = "tranfer.queue")
    public void receiveMessage(Message transfer) throws JMSException {
        final String body = transfer.getBody(String.class);
        try {
            TransferRequest transferRequest = objectMapper.readValue(body, TransferRequest.class);
            transferService.performTransferWithoutApproval(transferRequest.getFromAccountNumber(), transferRequest.getToAccountNumber(), transferRequest.getCurrency(), transferRequest.getAmount());
        } catch (JsonProcessingException e) {
            logger.error("Unable to deserialize JMS message");
        }
        doSomething();
    }

    public void doSomething() {
        //..
    }
}
