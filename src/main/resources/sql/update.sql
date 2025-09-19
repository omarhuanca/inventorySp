-- 31-01-25 update table code
ALTER TABLE ms_measurement ADD CONSTRAINT uq_ms_code UNIQUE(ms_code);

ALTER TABLE cn_coin ADD CONSTRAINT uq_cn_code UNIQUE(cn_code);

ALTER TABLE ln_line ADD CONSTRAINT uq_ln_name UNIQUE(ln_name);

ALTER TABLE prv_provider ADD CONSTRAINT uq_prv_name UNIQUE(prv_name);