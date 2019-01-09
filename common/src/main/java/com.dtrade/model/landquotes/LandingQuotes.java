package com.dtrade.model.landquotes;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;


@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
public class LandingQuotes {

    @Id
    @GeneratedValue
    private Long id;

    private Long time;

    private String name;

    private String value;
}
