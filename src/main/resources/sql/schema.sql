/* **********************************************
 * SEQUENCES                                    *
 ************************************************/

CREATE SEQUENCE ln_seq;
CREATE SEQUENCE ms_seq;
CREATE SEQUENCE cn_seq;
CREATE SEQUENCE st_seq;
CREATE SEQUENCE pr_seq;
CREATE SEQUENCE prv_seq;
CREATE SEQUENCE stcr_seq;

/*==============================================================*/
/* Table: Line                                                  */
/*==============================================================*/
CREATE TABLE ln_line (
    ln_id       BIGINT      NOT NULL,
    ln_name     VARCHAR(45) NOT NULL
);

ALTER TABLE ln_line
    ALTER COLUMN    ln_id       SET DEFAULT nextval('ln_seq'),
    ADD CONSTRAINT  pk_ln_id    PRIMARY KEY(ln_id);

/*==============================================================*/
/* Table: Measurement                                           */
/*==============================================================*/
CREATE TABLE ms_measurement (
    ms_id       BIGINT          NOT NULL,
    ms_code     VARCHAR(45)     NOT NULL
);

ALTER TABLE ms_measurement
    ALTER COLUMN    ms_id       SET DEFAULT nextval('ms_seq'),
    ADD CONSTRAINT  pk_ms_id    PRIMARY KEY(ms_id);

/*==============================================================*/
/* Table: Coin                                                  */
/*==============================================================*/
CREATE TABLE cn_coin (
    cn_id       BIGINT          NOT NULL,
    cn_code     VARCHAR(45)     NOT NULL
);

ALTER TABLE cn_coin
    ALTER COLUMN    cn_id       SET DEFAULT nextval('cn_seq'),
    ADD CONSTRAINT  pk_cn_id    PRIMARY KEY(cn_id);

/*==============================================================*/
/* Table: Stock                                                 */
/*==============================================================*/
CREATE TABLE st_stock (
    st_id       BIGINT          NOT NULL,
    st_code     VARCHAR(45)     NOT NULL,
    st_value    INTEGER         NOT NULL,
    st_ms_id    BIGINT          NOT NULL
);

ALTER TABLE st_stock
    ALTER COLUMN    st_id           SET DEFAULT nextval('st_seq'),
    ADD CONSTRAINT  pk_st_id        PRIMARY KEY(st_id),
    ADD CONSTRAINT  uq_st_code      UNIQUE(st_code),
    ADD CONSTRAINT  fk_st_ms_id     FOREIGN KEY(st_ms_id) REFERENCES ms_measurement(ms_id) ON UPDATE CASCADE ON DELETE CASCADE;

/*==============================================================*/
/* Table: Price                                                 */
/*==============================================================*/
CREATE TABLE pr_price (
    pr_id       BIGINT          NOT NULL,
    pr_code     VARCHAR(45)     NOT NULL,
    pr_value    INTEGER         NOT NULL,
    pr_cn_id    BIGINT          NOT NULL
);

ALTER TABLE pr_price
    ALTER COLUMN    pr_id           SET DEFAULT nextval('pr_seq'),
    ADD CONSTRAINT  pk_pr_id        PRIMARY KEY(pr_id),
    ADD CONSTRAINT  fk_pr_cn_id    	FOREIGN KEY(pr_cn_id) REFERENCES cn_coin(cn_id) ON UPDATE CASCADE ON DELETE CASCADE;


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
    ADD CONSTRAINT  pk_prv_id        PRIMARY KEY(prv_id);

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
