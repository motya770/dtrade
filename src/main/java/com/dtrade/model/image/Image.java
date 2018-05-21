package com.dtrade.model.image;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    @NotEmpty
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] pic;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Diamond diamond;
}
