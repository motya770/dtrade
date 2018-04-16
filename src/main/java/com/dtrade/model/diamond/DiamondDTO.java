package com.dtrade.model.diamond;

import com.dtrade.model.quote.Quote;
import lombok.Data;

@Data
public class DiamondDTO extends Diamond {

    private Quote quote;
}
