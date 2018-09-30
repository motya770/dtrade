package com.dtrade.service.impl;

public class OLD {

     /*
    private ScheduledExecutorService executorService;

    private static final long HALF_HOUR = 30 * 60 * 1_000;

    @PostConstruct
    public void init(){
        executorService = Executors.newScheduledThreadPool(25);
        pool();
    }
    public void pool(){
        Runnable runnable  = ()-> {
            List<CoinPayment> coinPayments = coinPaymentRepository.getNotConfirmentCoinPayments();
            coinPayments.forEach(coinPayment -> {

                if(coinPayment.getCoinPaymentType().equals(CoinPaymentType.DEPOSIT)){
                    this.poolDeposit(coinPayment);
                }else if(coinPayment.getCoinPaymentType().equals(CoinPaymentType.WITHDRAW){

                    if(coinPayment.getInWithdrawRequest().getStatus() == 2){
                        long duration = System.currentTimeMillis() - coinPayment.getLastTimePooled();
                        if(duration < HALF_HOUR){
                            return;
                        }
                    }
                    this.poolWithdraw(coinPayment);
                }
            });
        };
        executorService.scheduleAtFixedRate(runnable, 1, 5, TimeUnit.MINUTES);
    }*/

     /*

    //@Override
    public  void login() {
        String body = "version=1&cmd=rates&key=" + publicKey + "&format=json";;
        String result = requestServer(body);
        System.out.println(result);
    }

    //@Override
    public  void deposit() {
        RestTemplate restTemplate = new RestTemplate();

        double amount=10000;
        String buyer_email = "motya770@gmail.com";
        String body = "currency1=USD&currency2=BTC&version=1&cmd=create_transaction&key=" + publicKey+ "&amount=" + amount +
                "&buyer_email=" + buyer_email + "&format=json";
        String result = requestServer(body);
        System.out.println(result);
    }

    //@Override
    public void withdraw() {

        RestTemplate restTemplate = new RestTemplate();

        double amount=10000;
        String buyer_email = "motya770@gmail.com";
        String address = "";
        String body = "currency=BTC&currency2=USD&version=1&cmd=create_withdrawal&key=" + publicKey+ "&amount=" + amount +
                "&buyer_email=" + buyer_email + "&format=json&address=" + address + "&=" ;

        String result = requestServer(body);
        System.out.println(result);
    }

    public static void main(String... args) throws Exception{}*/

     /*
            for(int j=0; j<3; j++) {
                System.out.println("start: " + j);
                Pair<TradeOrder, List<TradeOrder>> buySell = bookOrderService.find10Closest(entry.getKey());
                if (buySell == null) {
                    break;
                }

                TradeOrder buyOrder = buySell.getFirst();
                List<TradeOrder> sellOrders = buySell.getSecond();

                if(!checkIfCanExecute(Pair.of(buyOrder, sellOrders.get(0)))){
                    break;
                }

                Pair<Boolean, Boolean> result = null;
                for (int i = 0; i < sellOrders.size(); i++) {
                    TradeOrder sellOrder = sellOrders.get(i);
                    Pair<TradeOrder, TradeOrder> pair = Pair.of(buyOrder, sellOrder);

                    if (checkIfCanExecute(pair)) {
                        Runnable quoteRunnable = () -> quotesService.issueQuote(pair);
                        executor.execute(quoteRunnable);
                        logger.debug("EXECUTING TRADE PAIR");

                        System.out.println("ij: "  + i + " "  + j + " " + buyOrder.getId() + " " + sellOrder.getId());

                        long start = System.currentTimeMillis();

                                    transactionTemplate.execute(status -> {
                                        return executeTradeOrders(pair);
                                    });


                        if (!result.getFirst()) {
                            //can't execute or buy is executed
                            System.out.println("break1");
                            break;
                        }

                        logger.info("execute trade time: {}", (System.currentTimeMillis() - start));
                    }else {
                        System.out.println("can't execute");
                        break;
                    }
                }

                if (result==null || !result.getFirst()) {
                    System.out.println("break2");
                    break;
                }
            }*/

            /*
            List<TradeOrder> buyList = buySell.getFirst();
            List<TradeOrder> sellList = buySell.getSecond();

            List<Boolean> buyResults = new ArrayList<>();
            List<Boolean> sellResults  = new ArrayList<>();

            for(int i=0; i < buyList.size(); i++){
                Pair<Boolean, Boolean>  executedResult = null;
                for(int j=0; j < sellList.size(); j++){

                    TradeOrder buy = buyList.get(i);
                    TradeOrder sell = sellList.get(j);

                    System.out.println("buy: " + i  + " " + buy.getId() + " " + Thread.currentThread().getName());
                    System.out.println("sell: " + j + " " + sell.getId());

                    Pair<TradeOrder, TradeOrder> pair =  Pair.of(buy, sell);

                    if(checkIfCanExecute(pair)){

                        executedResult = transactionTemplate
                                .execute(status -> {
                          return executeTradeOrders(pair);
                        });

                        System.out.println("result: " + executedResult.getFirst()+ " " + executedResult.getSecond());

                        Boolean buyResult =  executedResult.getFirst();
                        if(!buyResult){
                            break;
                        }

                        Boolean sellResult = executedResult.getSecond();
                        if(!sellResult){

                            continue;
                        }
                    }else{

                          return;
                    }

                }

                if(executedResult.getFirst()==true){
                    break;
                }
            }*/

    //System.out.println("Main execution: " + (System.currentTimeMillis() - start1));

            /*
            List<Pair<TradeOrder, TradeOrder>> pairs =
            if(pairs!=null && pairs.size()>0){
                pairs.forEach(pair->{
                    if(checkIfCanExecute(pair)) {

                        Runnable quoteRunnable = () -> quotesService.issueQuote(pair);
                        executor.execute(quoteRunnable);
                        logger.debug("EXECUTING TRADE PAIR");

                        long start = System.currentTimeMillis();
                        transactionTemplate.execute(status -> {
                            executeTradeOrders(pair);
                            return status;
                        });


                        logger.debug("execute trade time: {}", (System.currentTimeMillis() - start));
                    }
                });
            }*/

    //System.out.println("Main execuiton: " + (System.currentTimeMillis() - start1));
}
