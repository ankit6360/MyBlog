package com.myblog.myblog.entity;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
//import java.util.HashSet;
//import java.util.Set;


@Getter//if we use getter and stter annotation than we dont use @data annotation data can use instead of getter and setter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="Posts",
        uniqueConstraints={@UniqueConstraint(columnNames = {"title"})}
)

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private long id;
    @Column(name="title",nullable= false)
    private String title;
    @Column(name="description",nullable=false)
    private String description;
    @Column(name="content",nullable=false)
    private String content;
    @OneToMany(mappedBy="post",cascade=CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();

}
