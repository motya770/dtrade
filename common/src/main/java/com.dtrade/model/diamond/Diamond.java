package com.dtrade.model.diamond;

import com.dtrade.model.account.Account;
import com.dtrade.model.config.AssetType;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by kudelin on 8/24/16.
 */

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Diamond implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String ticketName;

    @NotNull
    private TicketProvider ticketProvider;

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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency baseCurrency;

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

    @JsonIgnore
    @Column(precision=19, scale=8)
    private BigDecimal roboHighEnd;

    @JsonIgnore
    @Column(precision=19, scale=8)
    private BigDecimal roboLowEnd;

    @NotNull
    @JsonIgnore
    @Column(precision=19, scale=8)
    private BigDecimal roboMaxAmount;

    @JsonIgnore
    private Long lastRoboUpdated;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    /*
    @Column(columnDefinition = "boolean default true")
    private boolean baseCurrency;
    */

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
