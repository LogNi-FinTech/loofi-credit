package com.logni.credit.service.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@TypeDefs(value = { @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class), @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
@NoArgsConstructor
@AllArgsConstructor
public class LoanProduct extends Auditable<String> {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   @NotBlank String name;

   String description;

   @Positive BigDecimal interestRate;

   @Positive BigDecimal loanFee;

   @Positive BigDecimal maxAmount;

   @Positive BigDecimal minAmount;

   @Positive BigDecimal defaultPeriod;

   @Positive BigDecimal penaltyRate;

   @Positive Integer approvalLevel;

   @Positive Integer maxPeriod;

   @Positive Integer minPeriod;

}
