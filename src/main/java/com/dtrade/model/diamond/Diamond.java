package com.dtrade.model.diamond;

import com.dtrade.model.account.Account;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by kudelin on 8/24/16.
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Diamond implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @NotNull
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiamondType diamondType;

    @NotNull
    private BigDecimal carats;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Cut cut;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Color color;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Clarity clarity;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiamondStatus diamondStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private Integer score;

    @NotNull
    private BigDecimal totalStockAmount;

    private boolean hideTotalStockAmount;

   // @Embedded
   // private DiamondCategory diamondCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "diamond", fetch = FetchType.LAZY)
    private List<Image> images;

    @Override
    public String toString(){
        return "diamond: {id:" + id +  "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Diamond)) {
            return false;
        }
        Diamond diamond = (Diamond) o;
        return Objects.equals(id, diamond.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
