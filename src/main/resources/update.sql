CREATE INDEX trade_order_execution_date_index ON trade_order (execution_date) USING BTREE;
CREATE INDEX quote_time_index ON quote (time) USING BTREE;