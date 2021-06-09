package com.example.demo.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "vms")
public class Vm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Vm(Boolean isFree) {
        this.isFree = isFree;
    }
}
