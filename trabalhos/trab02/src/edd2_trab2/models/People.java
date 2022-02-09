/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd2_trab2.models;

import java.util.Objects;

/**
 *
 * @author pedro
 */
public class People implements Comparable<Object>{
  private String cpf;
  private String name;
  private String age;

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 73 * hash + Objects.hashCode(this.cpf);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final People other = (People) obj;
    if (!Objects.equals(this.cpf, other.cpf)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.age, other.age)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(Object t) {
    if (t == null) throw new IllegalArgumentException("'compareTo()' argument cannot be null");
    if (t instanceof People == false) throw new IllegalArgumentException("'compareTo()' argument must be People instance.");
    People p = (People) t;
    return this.cpf.compareTo(p.cpf);
  }
  
  
}
