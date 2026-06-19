package bo.umss.app.inventorySp.business.product.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Index;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import bo.umss.app.inventorySp.business.buy.model.StockBuy;
import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.referral.model.StockReferral;
import bo.umss.app.inventorySp.exception.EmptyFieldException;
import bo.umss.app.inventorySp.exception.NegativeFieldException;
import bo.umss.app.inventorySp.exception.ValueLessThanOtherException;

@Entity
@Table(name = "prd_product", uniqueConstraints = {@UniqueConstraint(columnNames = {"prd_code"})}, indexes = {@Index(name = "idx_prd_ln_id", columnList = "prd_ln_id")})
public class Product implements Serializable {

    private static final long serialVersionUID = -3585952891558067223L;

    public static final String CODE_CAN_NOT_BE_BLANK = "Code can not be blank";
    public static final String DESCRIPTION_CAN_NOT_BE_BLANK = "Description can not be blank";
    public static final String STOCK_CAN_NOT_BE_LESS_THAN_ZERO = "Stock can not be less zero";
    public static final String MEASUREMENT_CAN_NOT_BE_NULL = "Measurement can not be null";
    public static final String AMOUNT_GREATER_THAN_AVAILABLE = "Amount greater than available";
    public static final String CODE_PRODUCT_DUPLICATE = "Code product already exists";
    public static final String PRICE_COST_CAN_NOT_BE_LESS_ZERO = "Price cost can not be less than zero";
    public static final String PRICE_COST_CAN_NOT_BE_NULL = "Price cost can not be null";
    public static final String PRICE_SALE_CAN_NOT_BE_NULL = "Price sale can not be null";
    public static final String PRICE_SALE_CAN_NOT_BE_LESS_ZERO = "Price sale can not be less than zero";
    public static final String IMAGE_CAN_NOT_BE_NULL = "Image can not be null";
    public static final String COIN_CAN_NOT_BE_NULL = "Coin can not be null";
    public static final String LINE_CAN_NOT_BE_NULL = "Line can not be null";
    public static final String PROVIDER_CAN_NOT_BE_NULL = "Provider can not be null";
    public static final String PRICE_COST_COIN_DIFF_PRICE_SALE_COIN = "Coin diff between price cost and price sale";
    public static final String PRICE_SALE_CHEAPER_THAN_PRICE_COST = "Price sale can not be cheaper than price cost";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "prdc_product", sequenceName = "prd_seq", initialValue = 1000)
    @Column(name = "prd_id")
    private Long id;

    @NotNull
    @Column(name = "prd_code")
    private String code;

    @NotNull
    @Column(name = "prd_description")
    private String description;

    @NotNull
    @Column(name = "prd_stock")
    private Integer stock;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prd_ms_id", nullable = false)
    private Measurement measurement;

    @NotNull
    @Column(name = "prd_price_cost")
    private Double priceCost;

    @NotNull
    @Column(name = "prd_price_sale")
    private Double priceSale;

    @Column(name = "prd_image")
    private byte[] image;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prd_cn_id", nullable = false)
    private Coin coin;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prd_ln_id", nullable = false)
    private Line line;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prd_prv_id", nullable = false)
    private Provider provider;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chp_id")
    private List<ChangePrice> listChangePriceCost;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stc_id")
    private List<StockBuy> listStockBuy;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stcr_id")
    private List<StockReferral> listStockReferral;

    public Product(String code, String description, Integer stock, Measurement measurement, Double priceCost, Double priceSale, Coin coin,
                   byte[] image, Line line, Provider provider) {
        this.code = code;
        this.description = description;
        this.stock = stock;
        this.measurement = measurement;
        this.priceCost = priceCost;
        this.priceSale = priceSale;
        this.image = image;
        this.coin = coin;
        this.line = line;
        this.provider = provider;
        listChangePriceCost = new ArrayList<>();
        listStockBuy = new ArrayList<>();
        listStockReferral = new ArrayList<>();
    }

    public static Product at(String code, String description, Integer stock, Measurement measurement, Double priceCost, Double priceSale, Coin coin,
                             byte[] image, Line line, Provider provider) {
        if (code.isEmpty())
            throw new EmptyFieldException(CODE_CAN_NOT_BE_BLANK);
        if (description.isEmpty())
            throw new EmptyFieldException(DESCRIPTION_CAN_NOT_BE_BLANK);
        if (0 > stock)
            throw new NegativeFieldException(STOCK_CAN_NOT_BE_LESS_THAN_ZERO);
        if (null == measurement)
            throw new RuntimeException(MEASUREMENT_CAN_NOT_BE_NULL);
        if (0 >= priceCost)
            throw new NegativeFieldException(PRICE_COST_CAN_NOT_BE_LESS_ZERO);
        if (0 >= priceSale)
            throw new NegativeFieldException(PRICE_SALE_CAN_NOT_BE_LESS_ZERO);
        if (priceCost > priceSale)
            throw new ValueLessThanOtherException(PRICE_SALE_CHEAPER_THAN_PRICE_COST);
        if (null == image)
            throw new RuntimeException(IMAGE_CAN_NOT_BE_NULL);
        if (null == coin)
            throw new RuntimeException(COIN_CAN_NOT_BE_NULL);
        if (null == line)
            throw new EmptyFieldException(LINE_CAN_NOT_BE_NULL);
        if (null == provider)
            throw new EmptyFieldException(PROVIDER_CAN_NOT_BE_NULL);

        return new Product(code, description, stock, measurement, priceCost, priceSale, coin, image, line, provider);
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String potentialCode) {
        code = potentialCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String potentialDescription) {
        description = potentialDescription;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer potentialStock) {
        stock = potentialStock;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement potentialMeasurement) {
        measurement = potentialMeasurement;
    }

    public Double getPriceCost() {
        return priceCost;
    }

    public void setPriceCost(Double potentialPriceCost) {
        priceCost = potentialPriceCost;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Double potentialPriceSale) {
        priceSale = potentialPriceSale;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line potentialLine) {
        line = potentialLine;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider potentialProvider) {
        provider = potentialProvider;
    }

    public List<ChangePrice> getListChangePriceCost() {
        return listChangePriceCost;
    }

    public List<StockBuy> getListStockBuy() {
        return listStockBuy;
    }

    public List<StockReferral> getListReferral() {
        return listStockReferral;
    }

    public void setListReferral(List<StockReferral> listReferral) {
        this.listStockReferral = listReferral;
    }

    public boolean equals(Product potentialProduct) {
        return code.equalsIgnoreCase(potentialProduct.getCode());
    }

    public Boolean listTransactionCompareGreatherThanZero(Integer count) {
        return listStockBuy.size() > count;
    }

    public void addBuy(StockBuy buy) {
        if (amountGreaterThanZero()) {

            if (verifyPotentialStockGreaterZero(buy.getAmount())) {
                todoIncreaseStock(buy.getAmount());
            }
            listStockBuy.add(buy);
        }
    }

    public Boolean priceCostLessThanPriceSale() {
        return priceCost < priceSale;
    }

    public Boolean priceCostLessOtherPriceCost(Double potentialPriceCost) {
        return priceCost < potentialPriceCost;
    }

    public void changePriceBuy(Double potentialPriceCost, Integer stock) {
        if (priceCostLessOtherPriceCost(potentialPriceCost)) {
            LocalDate currentDate = LocalDate.now();
            ChangePrice changePrice = ChangePrice.at(potentialPriceCost, getPriceCost(), coin, stock, measurement, currentDate);
            getListChangePriceCost().add(changePrice);
            setPriceCost(potentialPriceCost);
        }

    }

    public Boolean canDecreaseStock(Integer amount) {
        return verifyValueGreaterThanPotentialStock(amount);
    }

    public void changeMesurementStock(Integer potentialStock) {
        if (compareOtherStock(potentialStock)) {
            setStock(potentialStock);
        }
    }

    public void addReferral(StockReferral referral) {
        todoDecrementStock(referral.getAmount());
        listStockReferral.add(referral);
    }

    public Double calculateSubtotalWithCoin() {
        return priceSale * stock;
    }

    public Double generateSubtotal() {
        return priceSale * stock;
    }

    public Boolean compareOtherCode(String potentialCode) {
        return code.equalsIgnoreCase(potentialCode);
    }

    public Boolean compareOtherDescription(String potentialDescription) {
        return description.equalsIgnoreCase(potentialDescription);
    }

    public Boolean compareStock(Integer potentialStock) {
        return stock.equals(potentialStock);
    }

    public Boolean comparePriceSale(Double potentialPriceSale) {
        return priceSale.equals(potentialPriceSale);
    }

    public Boolean comparePriceCost(Double potentialPriceCost) {
        return priceCost.equals(potentialPriceCost);
    }

    public Boolean compareLine(Line potentialLine) {
        return line.equals(potentialLine);
    }

    public Boolean compareProvider(Provider potentialProvider) {
        return provider.equals(potentialProvider);
    }

    public Boolean amountGreaterThanZero() {
        return stock > 0;
    }

    public Boolean verifyPotentialStockGreaterZero(Integer potentialValue) {
        return potentialValue > 0;
    }

    public void todoIncreaseStock(Integer potentialValue) {
        if (stock > 0) {
            stock = stock + potentialValue;
        }
    }

    public Boolean verifyValueGreaterThanPotentialStock(Integer potentialValue) {
        return stock >= potentialValue;
    }

    public void todoDecrementStock(Integer potentialStock) {
        if (verifyValueGreaterThanPotentialStock(potentialStock)) {
            setStock(stock - potentialStock);
        } else {
            throw new RuntimeException(AMOUNT_GREATER_THAN_AVAILABLE);
        }
    }

    public Boolean compareOtherStock(Integer potentialValue) {
        return stock.equals(potentialValue);
    }
}
