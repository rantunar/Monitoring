package com.account.multidb.api.restmodel;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @NotNull private BigDecimal accountNumber;

  @NotNull @NotEmpty private String firstName;

  @NotEmpty private String lastName;

  @NotNull private Boolean isPrimary;
}
