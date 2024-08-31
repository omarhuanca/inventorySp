/* **********************************************
 * SEQUENCES                                    *
 ************************************************/

CREATE SEQUENCE ln_seq;
CREATE SEQUENCE ntp_seq;
CREATE SEQUENCE ms_seq;
CREATE SEQUENCE cn_seq;
CREATE SEQUENCE st_seq;
CREATE SEQUENCE pr_seq;

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
/* Table: Not Provided Provider                                 */
/*==============================================================*/
CREATE TABLE ntp_not_provided_provider (
    ntp_id          BIGINT          NOT NULL,
    ntp_code        VARCHAR(45)     NOT NULL,
    ntp_description VARCHAR(100)    NOT NULL,
    ntp_ln_id       BIGINT          NOT NULL
);

ALTER TABLE ntp_not_provided_provider
    ALTER COLUMN    ntp_id          SET DEFAULT nextval('ntp_seq'),
    ADD CONSTRAINT  pk_ntp_id       PRIMARY KEY(ntp_id),
    ADD CONSTRAINT  fk_ntp_ln_id    FOREIGN KEY(ntp_ln_id) REFERENCES ln_line(ln_id) ON UPDATE CASCADE ON DELETE CASCADE;

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
