package com.devsu.querydatamanagement.infraestructure.controller;

import com.devsu.querydatamanagement.domain.interfaces.CommandWriteService;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.AccountRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.ClientRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.in.TransactionRequest;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.AccountResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.ClientResponse;
import com.devsu.querydatamanagement.infraestructure.controller.dto.out.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/update-accounts")
    ResponseEntity<List<AccountResponse>> loadAccountsData() {
        return  new ResponseEntity<>(commandWriteService.loadAccountsData(), HttpStatus.OK);
    }

    @PostMapping("/create-client")
    ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) throws Exception {
        return new ResponseEntity<>(commandWriteService.SaveCustomer(clientRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/update-client/{id}")
    ResponseEntity<ClientResponse> updateClient(@RequestBody ClientRequest clientRequest, @PathVariable String id){
        return new ResponseEntity<>(commandWriteService.updateClient(clientRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete-client/{id}")
    ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        commandWriteService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create-account")
    ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) throws Exception {
        return new ResponseEntity<>(commandWriteService.createAccount(accountRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/update-account/{id}")
    ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountRequest accountRequest, @PathVariable String id) {
        return new ResponseEntity<>(commandWriteService.updateAccount(accountRequest, id), HttpStatus.OK);
    }

    @PostMapping("/create-transaction")
    ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) throws Exception {
        return new ResponseEntity<>(commandWriteService.saveTransaction(transactionRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/update-transaction/{id}")
    ResponseEntity<TransactionResponse> updateTransaction(@RequestBody TransactionRequest transactionRequest, @PathVariable String id) {
        return new ResponseEntity<>(commandWriteService.updateTransaction(transactionRequest, id), HttpStatus.OK);
    }

    @GetMapping("/validate-balance/{accountId}/{amount}")
    ResponseEntity<Boolean> validateBalance(@PathVariable Long accountId, @PathVariable BigDecimal amount) {
        return new ResponseEntity<>(commandWriteService.canProcessWithTransaction(accountId, amount), HttpStatus.OK);

    }

}
