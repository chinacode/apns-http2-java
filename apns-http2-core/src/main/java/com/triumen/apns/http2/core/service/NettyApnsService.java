package com.triumen.apns.http2.core.service;


import com.triumen.apns.http2.core.model.ApnsConfig;
import com.triumen.apns.http2.core.model.PushNotification;
import com.triumen.apns.http2.core.netty.NettyApnsConnection;
import com.triumen.apns.http2.core.netty.NettyApnsConnectionPool;

public class NettyApnsService extends AbstractApnsService {

    private NettyApnsConnectionPool connectionPool;


    private NettyApnsService(ApnsConfig config) {
        super(config);
        connectionPool = new NettyApnsConnectionPool(config);
    }

    public static NettyApnsService create(ApnsConfig apnsConfig) {
        return new NettyApnsService(apnsConfig);
    }



    @Override
    public int size() {
        return connectionPool.size();
    }

    @Override
    public void sendNotification(PushNotification notification) {
        executorService.execute(() -> {
            NettyApnsConnection connection = null;
            try {
                connection = connectionPool.acquire();
                if (connection != null) {
                    boolean result = connection.sendNotification(notification);
                    if (result == false) {
                        log.error("send msg failure {"+notification.toString()+"}");
                        pushFailureMsg(notification);
                    }
                    log.info("send msg success {"+notification.toString()+"}");
                }
            } catch (Exception e) {
                log.error("send msg failure {"+notification.toString()+"}");
                log.error("sendNotification:", e);
                pushFailureMsg(notification);
            } finally {
                connectionPool.release(connection);
            }
        });
    }

    @Override
    public boolean sendNotificationSynch(PushNotification notification) {
        NettyApnsConnection connection = null;
        try {
            connection = connectionPool.acquire();
            if (connection != null) {
                boolean result = connection.sendNotification(notification);
                return result;
            }
        } catch (Exception e) {
            log.error("sendNotification", e);
        } finally {
            connectionPool.release(connection);
        }
        return false;
    }

    private void pushFailureMsg(PushNotification notification){
        if(failureMsgList !=null) {
            failureMsgList.push(notification);
        }
    }



    @Override
    public void shutdown() {
        connectionPool.shutdown();
        super.shutdown();
    }



}
