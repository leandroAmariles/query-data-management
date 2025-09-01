package com.devsu.querydatamanagement.infraestructure.controller;

import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QueryDataController {

    private final CommandWriteService commandWriteService;


    @GetMapping("/update-clients")
    ResponseEntity<List<ClientResponse>> loadClientsData() {
        return new ResponseEntity<>(commandWriteService.loadClientsData(), HttpStatus.OK);
    }

    @GetMapping("/update-transactions")
    ResponseEntity<List<TransactionResponse>> loadTransactionsData() {
        return new ResponseEntity<>(commandWriteService.loadTransactionsData(), HttpStatus.OK);
    }

    @GetMapping("/update/accounts")
    ResponseEntity<List<AccountResponse>> loadAccountsData() {
        return  new ResponseEntity<>(commandWriteService.loadAccountsData(), HttpStatus.OK);
    }

}
