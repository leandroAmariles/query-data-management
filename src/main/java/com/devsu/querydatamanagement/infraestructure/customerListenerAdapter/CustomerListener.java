package com.devsu.querydatamanagement.infraestructure.customerListenerAdapter;

import com.devsu.querydatamanagement.infraestructure.customerListenerAdapter.dto.EventResponse;


public interface CustomerListener {

    EventResponse balanceCheck(String transactionRequest);

     EventResponse clientEvent(String eventJson);




}
