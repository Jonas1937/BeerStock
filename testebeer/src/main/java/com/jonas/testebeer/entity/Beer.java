package com.jonas.testebeer.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jonas.testebeer.enums.BeerType;

@Entity
public class Beer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    private String name;
   
    @NotNull
    @NotEmpty
    private String brand;
    
    @NotNull
    @Max(value = 200)
    private int quantity;
    
    @NotNull
    @Max(value = 200)
    private int max;
    
    private BeerType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public BeerType getType() {
        return type;
    }

    public void setType(BeerType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((brand == null) ? 0 : brand.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + max;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + quantity;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Beer other = (Beer) obj;
        if (brand == null) {
            if (other.brand != null)
                return false;
        } else if (!brand.equals(other.brand))
            return false;
        if (id != other.id)
            return false;
        if (max != other.max)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (quantity != other.quantity)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    public Beer(Long id, String name, String brand, int quantity, int max, BeerType type) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.max = max;
        this.type = type;
    }

    public Beer() {
    }
    
}
