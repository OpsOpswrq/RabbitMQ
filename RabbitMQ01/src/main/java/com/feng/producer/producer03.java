package com.feng.producer;

        import com.feng.utils.RabbitMQUtils;
        import com.rabbitmq.client.Channel;

        import java.io.IOException;
        import java.util.concurrent.TimeoutException;

public class producer03 {
    private static String QUEUE = "feng";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(QUEUE,true,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for(int i = 0;i<1000;i++){
            String message = i+" ";
            channel.basicPublish("",QUEUE,null,message.getBytes());
            boolean b = channel.waitForConfirms();
            if(b){
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时为"+(end-begin)+"ms");
    }
}
