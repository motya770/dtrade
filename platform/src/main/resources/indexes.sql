create index trade_order_trade_order_status on trade_order (trader_order_status) using HASH;
create index trade_order_trade_order_status_index on trade_order (trader_order_status_index) using HASH;
create index trade_order_execution_date_index on trade_order (execution_date) using BTREE;
create index trade_order_creation_date_index on trade_order (execution_date) using BTREE;
create index quote_time_index on quote (time) using BTREE;
create index balance_activity_create_date_index on balance_activity (create_date) using BTREE;
create index stock_account_diamond_index on stock (account_id, diamond_id) using HASH;
CREATE INDEX diamond_quotes_index ON quote (diamond_id, time);
CREATE INDEX diamond_trade_order_status_time on trade_order (diamond_id, trader_order_status_index, execution_date);
CREATE INDEX account_trade_order_status_time on trade_order (account_id, trader_order_status_index, creation_date);
ALTER TABLE balance_activity
  ADD INDEX balance_activity_account_date_index (account_id, create_date DESC)
