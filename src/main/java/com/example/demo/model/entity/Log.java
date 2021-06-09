package com.example.demo.model.entity;

import com.example.demo.model.role.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private UserRole userRole;
    @Column
    private String action;
    @Column
    private Date date;

    public Log(User user,UserRole userRole, String action, Date date) {
        this.user = user;
        this.userRole = userRole;
        this.action = action;
        this.date = date;
    }
}
