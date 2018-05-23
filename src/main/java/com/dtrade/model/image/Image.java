package com.dtrade.model.image;

import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
public class Image implements Serializable {

    //TODO get GUID for ui

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    @JsonIgnore
    @NotEmpty
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] pic;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Diamond diamond;

    public byte[] getPic(){
        if(this.pic!=null){
            return this.pic.clone();
        }
        return null;
    }

    public void setPic(byte[] pic){
        if(pic!=null) {
            this.pic = pic.clone();
        }
    }
}
