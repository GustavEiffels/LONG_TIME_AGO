package com.team.team_project.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;

@Entity
@Builder
@Table(name="CheckList")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert
@ToString(exclude = {"user","plan"})
public class CheckList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @Column(columnDefinition = "text")
    private String todo;

    @Column(columnDefinition = "varchar(20) default '준비'")
    private String done;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="pno")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="code")
    private User user;

    public void changeDone(String done){
        this.done=done;
    }
    public void changeTodo(String todo){
        this.todo=todo;
    }
}

