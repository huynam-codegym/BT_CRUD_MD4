package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Bạn đã để trống dữ liệu, mời nhập vào!")
    @Size(min = 3,max = 20,message = "Tên nhập vào phải là chữ có từ 2 đến 10 ký tự!")
    private String nickName;

    @OneToMany(targetEntity = Employee.class,cascade = CascadeType.ALL)
    @JsonIgnore
//    @JsonManagedReference
    private List<Employee> employees;

    public Boss() {
    }

    public Boss(Long id, String nickName, List<Employee> employees) {
        this.id = id;
        this.nickName = nickName;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
//                ", employees=" + employees +
                '}';
    }
}
