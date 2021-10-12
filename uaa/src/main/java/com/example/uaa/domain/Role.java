package com.example.uaa.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "date_created")
    @CreatedDate
    private Date dateCreated;

    @Column(name = "modifirer")
    @LastModifiedBy
    private String modifier;

    @Column(name = "date_modifirer")
    @LastModifiedBy
    private Date dateModified;
}
