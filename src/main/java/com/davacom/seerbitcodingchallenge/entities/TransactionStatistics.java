package com.davacom.seerbitcodingchallenge.entities;

import java.math.BigDecimal;

public record TransactionStatistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
}
