package com.account.multidb.api.controllers;

import com.account.multidb.api.restmodel.Account;
import com.account.multidb.api.restmodel.ErrorResponse;
import com.account.multidb.api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
@Slf4j
public class AccountController {

  @Autowired private AccountService accountService;

  @Operation(summary = "Post account create request")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "account created successfully",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Account.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "account create validation failed",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Application error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "415",
            description = "Unsupported media type",
            content = @Content)
      })
  @PostMapping(value = "/create")
  public ResponseEntity<String> create(@Validated @RequestBody Account account) {
    final String message = accountService.createAccount(account);
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  @Operation(summary = "Get account request")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "account list return successfully",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Account.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Application error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "415",
            description = "Unsupported media type",
            content = @Content)
      })
  @GetMapping(value = "/get")
  public ResponseEntity<List<Account>> get() {
    return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
  }
}
