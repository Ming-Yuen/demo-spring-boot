package com.demo.product.dto.manulife;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
@Data
public class MPFDailyResponse {
    private String classId;
    private String[] distOthers;
    private String[] distSecurities;
    private String[] distBanks;
    private Product[] products;
    private CalendarYearReturn calendarYearReturns;
    private String divFrequencyName;
    private String divFrequencyId;
    private String regionName;
    private String regionId;
    private String assetClassName;
    private String assetClassId;
    private String promotedOrder;
    private String displayOrder;
    private String isin;
    private String className;
    private String fundName;
    private String fundId;
    private String platformName;
    private String platformId;
    private String locale;
    private String assetManager;
    private String globalFundCode;
    private String productLine;
    private String displayName;
    private LocalDate fundLaunchDate;
    private String fundUmbrellaCode;
    private String currency;
    private String riskRating;
    private String riskRatingDescription;
    private String displayFrontend;
    private OffsetDateTime riskRatingAsOfDate;
    private String underlyingFundLink;
    private String underlyingFundName;
    private LocalDate classLaunchDate;
    private String dividendLink;
    private LocalDate recordDate;
    private String dividend;
    private BigDecimal annualizedYield;
    private String fromCapital;
    private String fromNDI;
    private String[] paymentTypes;
    private String[] ilpProduct;
    private String[] productsId;
    private CumulativeReturn cumulativeReturns;
    private Nav nav;
    private String subManager;
    private String fundUmbrellaName;
    @Data
    private static class CumulativeReturn{
        private String asOfDate;
        private Period[] periods;
    }
    @Data
    private static class Period{
        private String period;
        private BigDecimal value;
    }
    @Data
    public static class Nav{
        private LocalDate asOfDate;
        private BigDecimal price;
        private String currency;
        private String aum;
        private String aumCurrency;
        private String redemptionPrice;
        private BigDecimal prevPrice;
        private BigDecimal changePrice;
        private BigDecimal changePercent;
        private String offerPrice;
        private String offer3Price;
        private String offer5Price;
        private String fundIntRate;
        private String dividendAnnuYield;
        private LocalDate asOfDateInt;
        private String fundSuspendInd;
        private String fundInterestInd;
        private String dividendRecordDate;
        private String dividendPayoutRate;
        private String shareClassId;
        private String purchasePrice;
    }
    @Data
    private static class CalendarYearReturn{
        private String asOfDate;
        private Period[] periods;
    }
    @Data
    private static class Product{
        private String id;
        private String name;
        private String url;
        private boolean legacy;
    }
}
