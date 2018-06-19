package com.dtrade.model.diamond;

import com.dtrade.model.image.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DiamondDTO extends Diamond {

    @JsonProperty
    @Override
    public List<Image> getImages() {
        return super.getImages();
    }

    @Override
    public boolean equals(Object obj) {
       return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
