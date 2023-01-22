package com.logni.credit.service.model;

import com.logni.credit.service.model.enumaration.Status;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
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
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
@TypeDefs(value = {
        @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
})
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@ManyToOne()
	private LoanDocument loanDocument;

	@ManyToOne()
	private LoanProduct loanProduct;
	
	@Enumerated(EnumType.STRING)
	@Type(type = "pgsql_enum")
	private Status status;
	
	@Positive
	Integer customerId;
	
	@Positive
	Integer period;
	
	@Positive
	BigDecimal loanAmount;

	@NotBlank
	String name; 

	@NotBlank
	String description;
}
