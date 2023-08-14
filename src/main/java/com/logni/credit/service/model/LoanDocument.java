package com.logni.credit.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class LoanDocument extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@OneToMany(mappedBy = "loanDocument")
	private List<DocumentFile> documentFiles;
	
	@JsonIgnore
	@OneToMany(mappedBy = "loanDocument")
	private List<LoanApplication> loanApplications;
	
	@NotBlank
	String name; 

	@NotBlank
	String description;
}
