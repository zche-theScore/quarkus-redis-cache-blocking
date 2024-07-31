package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "db_model")
public class DbModel extends PanacheEntity {
  public String name;

  public static DbModel findByName(String name){
    return find("name", name).firstResult();
  }

  public String toString() {
    return "(" + this.id + " => " + this.name + ")";
  }
}