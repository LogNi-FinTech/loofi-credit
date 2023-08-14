package com.logni.credit.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logni.credit.service.model.enumaration.Status;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.*;

import org.hibernate.annotations.Type;
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
@Builder(toBuilder = true)
public class LoanRepayment extends Auditable<String> {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   int id;

   @JsonIgnore
   @ManyToOne
   private Loan loan;

   @Enumerated(EnumType.STRING)
   @Type(type = "pgsql_enum")
   private Status status;

   String txnChannel;

   String txnNote;

   String tag;

   String txnId;

   BigDecimal principleAmount;

   BigDecimal interestAmount;
// (principleAmount + interestAmount)
   BigDecimal totalAmount;

   // (paidPrincipleAmount + paidInterestAmount)
   BigDecimal paidAmount;

   BigDecimal paidPrincipleAmount;

   BigDecimal paidInterestAmount;

   BigDecimal unPaidPrincipleAmount;

   BigDecimal unPaidInterestAmount;

}
