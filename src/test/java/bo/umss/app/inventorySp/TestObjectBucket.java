package bo.umss.app.inventorySp;

import java.time.LocalDate;

import bo.umss.app.inventorySp.business.buy.model.StockBuy;
import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;
import bo.umss.app.inventorySp.business.coin.model.Coin;
import bo.umss.app.inventorySp.business.line.model.Line;
import bo.umss.app.inventorySp.business.measurement.model.Measurement;
import bo.umss.app.inventorySp.business.price.model.Price;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.referral.model.StockReferral;
import bo.umss.app.inventorySp.business.stock.model.Stock;

public class TestObjectBucket {

	public static final String JUAN_PEREZ_NAME = "Juan Perez";
	public static final String JUAN_PEREZ_NIT = "123456";
	public static final String JUAN_PEREZ_CELLPHONE = "71476576";
	public static final String PLATE_PURCHEASE_DESCRIPTION = "PLATO ZETA BOWL 8 PORCELANA CUADRADO";
	public static final String CUP_PURCHASE_DESCRIPTION = "TAZA ISAYLIN PORCELANA RECTO";
	public static final String BOWL7_DESCRIPTION = "bowl7 a round plate of porcelain";
	public static final String PLATE_CODE = "PLA-1";
	public static final String PLATE_NAME = "bowl8 porcelain";
	public static final String CUP_CODE = "CUP-1";
	public static final String CUP_NAME = "cup porcelain isaylin";
	public static final String BOWL8_CODE = "PLA-2";
	public static final String BOWL8_DESCRIPTION = "bowl8 porcelain plate";
	public static final String CODE_PZA = "pza";
	public static final String CODE_DOC = "doc";
	public static final String CODE_USA = "USD";
	public static final String CODE_BS = "BS";
	public static final String POT_CODE = "POT-1";
	public static final String POT_NAME = "Set of pot";
	public static final String INVOICE_NUMBER = "654987";

	public Product createPlate() {
		Line line = createLinePlate();
		Coin coin = createCoin(CODE_BS);
		Price priceCost = createPrice("PR-1", 5.0, coin);
		Price priceSale = createPrice("PR-2", 10.0, coin);
		Measurement measurement = createMeasurementPiece();
		Stock stock = createStock("ST-1", 10, measurement);
		Provider provider = Provider.at(JUAN_PEREZ_NAME, JUAN_PEREZ_CELLPHONE);

		return Product.at(PLATE_CODE, "PLATO ZETA BOWL 8 PORCELANA CUADRADO", stock, priceCost, priceSale, line,
				provider);
	}

	public Price createPrice(String code, Double value, Coin coin) {
		return Price.at(code, value, coin);
	}

	public Stock createStock(String code, Integer value, Measurement measurement) {
		return Stock.at(code, value, measurement);
	}

	public Coin createCoin(String code) {
		return Coin.at(code);
	}

	public Measurement createMeasurementPiece() {
		return Measurement.at(CODE_PZA);
	}

	public Product createCup() {
		Line line = createLineCup();
		Coin coin = createCoin(CODE_BS);
		Price priceCost = createPrice("PR-1", 8.0, coin);
		Price priceSale = createPrice("PR-2", 16.0, coin);
		Measurement measurement = createMeasurementPiece();
		Stock stock = createStock("ST-1", 10, measurement);
		Provider provider = Provider.at(JUAN_PEREZ_NAME, JUAN_PEREZ_CELLPHONE);

		return Product.at(CUP_CODE, CUP_PURCHASE_DESCRIPTION, stock, priceCost, priceSale, line, provider);
	}

	public LocalDate createDate() {
		return LocalDate.of(2024, 05, 15);
	}

	public Product createPot() {
		Line line = Line.at(POT_NAME);
		Coin coin = Coin.at(CODE_USA);
		Price priceCost = createPrice("PR-1", 205.0, coin);
		Price priceSale = createPrice("PR-2", 246.0, coin);
		Measurement measurement = createMeasurementPiece();
		Stock stock = createStock("ST-1", 80, measurement);
		Provider provider = Provider.at(JUAN_PEREZ_NAME, JUAN_PEREZ_CELLPHONE);

		return Product.at(POT_CODE, "OLLA TRILLIUM INOX 3 PCS", stock, priceCost, priceSale, line, provider);
	}

	public Line createLinePlate() {
		return Line.at(PLATE_NAME);
	}

	public Line createLineCup() {
		Line line = Line.at(CUP_NAME);
		return line;
	}

	public Provider createDefaultProvider() {
		return Provider.at(JUAN_PEREZ_NAME, JUAN_PEREZ_CELLPHONE);
	}

	public StockBuy createStockBuyPlate() {
		Product product = createPlate();
		Integer amount = 10;
		LocalDate localDate = LocalDate.now();
		String description = "buy plate";

		return StockBuy.at(product, amount, localDate, description);
	}

	public StockBuy createStockBuyCup() {
		Product product = createCup();
		Integer amount = 5;
		LocalDate localDate = LocalDate.now();
		String description = "buy cup";

		return StockBuy.at(product, amount, localDate, description);
	}

	public StockReferral createStockReferralPot() {
		Product product = createPot();
		Integer amount = 10;
		LocalDate localDate = LocalDate.now();

		return StockReferral.at(product, amount, localDate);
	}

	public StockReferral createStockReferralPlate() {
		Product product = createPlate();
		Integer amount = 25;
		LocalDate localDate = LocalDate.now();

		return StockReferral.at(product, amount, localDate);
	}

	public ChangePrice createChangePrice() {
		Coin coin = createCoin(CODE_BS);
		Price newPrice = createPrice("PR-1", 5.0, coin);
		Price oldPrice = createPrice("PR-2", 5.0, coin);
		Measurement measurement = createMeasurementPiece();
		Stock stock = createStock("ST-1", 80, measurement);

		return ChangePrice.at(newPrice, oldPrice, stock, LocalDate.now());
	}
}
