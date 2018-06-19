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
}
