package com.logni.credit.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table
@Entity
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFile extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@JsonIgnore
	@ManyToOne()
	private LoanDocument loanDocument;
	
	@NotBlank
	String fileName;
	
	@NotBlank
	String fileUrl;
}
