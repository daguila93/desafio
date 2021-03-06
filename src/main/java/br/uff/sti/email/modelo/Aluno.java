/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.sti.email.modelo;

/**
 *
 * @author edil
 */
public class Aluno {

    private String nome;
    private Long matricula;
    private String telefone;
    private String email;
    private String uffMail;
    private String status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUffMail() {
        return uffMail;
    }

    public void setUffMail(String uffMail) {
        this.uffMail = uffMail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Boolean isAtivo(){
        return "Ativo".equalsIgnoreCase(this.getStatus());
    }
    
    public Boolean hasUffmail(){
        return !this.getUffMail().isEmpty();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(nome).append(",")
                .append(matricula).append(",")
                .append(telefone).append(",")
                .append(email).append(",")
                .append(uffMail).append(",")
                .append(status)
                .toString();        
    }

}
