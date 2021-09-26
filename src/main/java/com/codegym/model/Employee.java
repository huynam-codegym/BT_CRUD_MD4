package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Bạn đã để trống dữ liệu, mời nhập vào!")
    @Size(min = 3,max = 20,message = "Tên nhập vào phải là chữ có từ 3 đến 20 ký tự!")
    private String name;

    @Min(value = 18,message = "Tuổi phải từ 18 trở lên !")
    private int age;
    @NotEmpty(message = "Bạn đã để trống dữ liệu, mời nhập vào!")
    private String address;

    private String img;

    @Transient
    private MultipartFile imgMultipartFile;

    private String audio;

    @Transient
    private MultipartFile audioMultipartFile;

    @ManyToOne
    @JoinColumn(name = "boss_id")
    @JsonBackReference
    private Boss boss;

    public Employee() {
    }

    public Employee(Long id, String name, int age, String address, String img
            , MultipartFile imgMultipartFile, String audio, MultipartFile audioMultipartFile, Boss boss) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.img = img;
        this.imgMultipartFile = imgMultipartFile;
        this.audio = audio;
        this.audioMultipartFile = audioMultipartFile;
        this.boss = boss;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public MultipartFile getImgMultipartFile() {
        return imgMultipartFile;
    }

    public void setImgMultipartFile(MultipartFile imgMultipartFile) {
        this.imgMultipartFile = imgMultipartFile;
    }

    public MultipartFile getAudioMultipartFile() {
        return audioMultipartFile;
    }

    public void setAudioMultipartFile(MultipartFile audioMultipartFile) {
        this.audioMultipartFile = audioMultipartFile;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", img='" + img + '\'' +
                ", imgMultipartFile=" + imgMultipartFile +
                ", audio='" + audio + '\'' +
                ", audioMultipartFile=" + audioMultipartFile +
                ", boss=" + boss +
                '}';
    }
}
