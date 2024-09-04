package com.heredi.nowait.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor único para el ID
    private Long id; // Identificador único del usuario

    @Column(nullable = false)
    private String name; // Nombre del usuario

    @Column(nullable = false)
    private String nickName; // Apodo del usuario

    @Column(nullable = false)
    private String email; // Correo electrónico del usuario

    @Column(nullable = false)
    private String phoneNumber; // Número de teléfono del usuario

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<PaymentInfo> paymentInfos;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Business business;

    // Constructor con todos los atributos
    public User(String name, String nickName, String email, String phoneNumber, List<PaymentInfo> paymentInfos, Business business) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.paymentInfos = paymentInfos;
        this.business = business;
    }

    // Getters y setters
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<PaymentInfo> getPaymentInfos() {
        return paymentInfos;
    }

    public void setPaymentInfos(List<PaymentInfo> paymentInfos) {
        this.paymentInfos = paymentInfos;
    }

    public Business getBusiness(){
        return business;
    }

    public void setBusiness(Business business){
        this.business = business;
    }
}
