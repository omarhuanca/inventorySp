/* **********************************************
 * SEQUENCES                                    *
 ************************************************/

CREATE SEQUENCE ln_seq;
CREATE SEQUENCE ms_seq;
CREATE SEQUENCE cn_seq;
CREATE SEQUENCE prv_seq;
CREATE SEQUENCE prd_seq;
CREATE SEQUENCE chp_seq;
CREATE SEQUENCE stcr_seq;
CREATE SEQUENCE stc_seq;

/*==============================================================*/
/* Table: Line                                                  */
/*==============================================================*/
CREATE TABLE ln_line (
    ln_id       BIGINT      NOT NULL,
    ln_name     VARCHAR(45) NOT NULL
);

ALTER TABLE ln_line
    ALTER COLUMN    ln_id       SET DEFAULT nextval('ln_seq'),
    ADD CONSTRAINT  pk_ln_id    PRIMARY KEY(ln_id),
    ADD CONSTRAINT  uq_ln_name  UNIQUE(ln_name);

/*==============================================================*/
/* Table: Measurement                                           */
/*==============================================================*/
CREATE TABLE ms_measurement (
    ms_id       BIGINT          NOT NULL,
    ms_code     VARCHAR(45)     NOT NULL
);

ALTER TABLE ms_measurement
    ALTER COLUMN    ms_id       SET DEFAULT nextval('ms_seq'),
    ADD CONSTRAINT  pk_ms_id    PRIMARY KEY(ms_id),
    ADD CONSTRAINT  uq_ms_code  UNIQUE(ms_code);

/*==============================================================*/
/* Table: Coin                                                  */
/*==============================================================*/
CREATE TABLE cn_coin (
    cn_id       BIGINT          NOT NULL,
    cn_code     VARCHAR(45)     NOT NULL
);

ALTER TABLE cn_coin
    ALTER COLUMN    cn_id       SET DEFAULT nextval('cn_seq'),
    ADD CONSTRAINT  pk_cn_id    PRIMARY KEY(cn_id),
    ADD CONSTRAINT  uq_cn_code  UNIQUE(cn_code);


/*==============================================================*/
/* Table: Provider                                              */
/*==============================================================*/
CREATE TABLE prv_provider (
    prv_id              BIGINT          NOT NULL,
    prv_name            VARCHAR(45)     NOT NULL,
    prv_phone_number    VARCHAR(45)     NOT NULL
);

ALTER TABLE prv_provider
    ALTER COLUMN    prv_id           SET DEFAULT nextval('prv_seq'),
    ADD CONSTRAINT  pk_prv_id        PRIMARY KEY(prv_id),
    ADD CONSTRAINT  uq_prv_name      UNIQUE(prv_name);

/*==============================================================*/
/* Table: Product                                               */
/*==============================================================*/
CREATE TABLE prd_product (
    prd_id          BIGINT          NOT NULL,
    prd_ms_id       BIGINT          NOT NULL,
    prd_cn_id       BIGINT          NOT NULL,
    prd_ln_id       BIGINT          NOT NULL,
    prd_prv_id      BIGINT          NOT NULL,
    prd_code        VARCHAR(45)     NOT NULL,
    prd_description VARCHAR(200)    NOT NULL,
    prd_stock       INTEGER         NOT NULL,
    prd_price_cost  NUMERIC         NOT NULL,
    prd_price_sale  NUMERIC         NOT NULL,
    prd_image       BYTEA           NOT NULL
);

ALTER TABLE prd_product
    ALTER COLUMN    prd_id             SET DEFAULT nextval('prd_seq'),
    ADD CONSTRAINT  pk_prd_id          PRIMARY KEY(prd_id),
    ADD CONSTRAINT  fk_prd_ms_id       FOREIGN KEY(prd_ms_id) REFERENCES ms_measurement(ms_id) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD CONSTRAINT  fk_prd_cn_id       FOREIGN KEY(prd_cn_id) REFERENCES cn_coin(cn_id) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD CONSTRAINT  fk_prd_ln_id       FOREIGN KEY(prd_ln_id) REFERENCES ln_line(ln_id) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD CONSTRAINT  fk_prd_prv_id      FOREIGN KEY(prd_prv_id) REFERENCES prv_provider(prv_id) ON UPDATE CASCADE ON DELETE CASCADE;


/*==============================================================*/
/* Table: ChangePrice                                           */
/*==============================================================*/
CREATE TABLE chp_change_price (
    chp_id              BIGINT          NOT NULL,
    chp_prd_id          BIGINT          NOT NULL,
    chp_cn_id           BIGINT          NOT NULL,
    chp_ms_id           BIGINT          NOT NULL,
    chp_new_price       NUMERIC         NOT NULL,
    chp_old_price       NUMERIC         NOT NULL,
    chp_stock           INTEGER         NOT NULL,
    chp_current_date    TIMESTAMP       NOT NULL
);

ALTER TABLE chp_change_price
    ALTER COLUMN    chp_id              SET DEFAULT nextval('chp_seq'),
    ADD CONSTRAINT  pk_chp_id           PRIMARY KEY(chp_id),
    ADD CONSTRAINT  fk_chp_cn_id        FOREIGN KEY(chp_cn_id) REFERENCES cn_coin(cn_id) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD CONSTRAINT  fk_chp_ms_id    	FOREIGN KEY(chp_ms_id) REFERENCES ms_measurement(ms_id) ON UPDATE CASCADE ON DELETE CASCADE;

/*==============================================================*/
/* Table: StockReferral                                         */
/*==============================================================*/
CREATE TABLE stcr_stock_referral (
    stcr_id         BIGINT          NOT NULL,
    stcr_prd_id     BIGINT          NOT NULL,
    stcr_amount     INTEGER         NOT NULL,
    stcr_local_date TIMESTAMP       NOT NULL
);

ALTER TABLE stcr_stock_referral
    ALTER COLUMN    stcr_id         SET DEFAULT nextval('stcr_seq'),
    ADD CONSTRAINT  pk_stcr_id      PRIMARY KEY(stcr_id),
    ADD CONSTRAINT  fk_stcr_prd_id  FOREIGN KEY(stcr_prd_id) REFERENCES prd_product(prd_id) ON UPDATE CASCADE ON DELETE CASCADE;

/*==============================================================*/
/* Table: StockBuy                                              */
/*==============================================================*/
CREATE TABLE stc_stock_buy (
    stc_id          BIGINT          NOT NULL,
    stc_prd_id      BIGINT          NOT NULL,
    stc_amount      INTEGER         NOT NULL,
    stc_local_date  TIMESTAMP       NOT NULL,
    stc_description VARCHAR(100)    NOT NULL
);

ALTER TABLE stc_stock_buy
    ALTER COLUMN    stc_id         SET DEFAULT nextval('stc_seq'),
    ADD CONSTRAINT  pk_stc_id      PRIMARY KEY(stc_id),
    ADD CONSTRAINT  fk_stc_prd_id  FOREIGN KEY(stc_prd_id) REFERENCES prd_product(prd_id) ON UPDATE CASCADE ON DELETE CASCADE;
