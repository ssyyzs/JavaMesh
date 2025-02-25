/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2020-2021. All rights reserved.
 */

package com.huawei.flowcontrol.core.config;

import com.huawei.flowcontrol.core.util.PluginConfigUtil;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

/**
 * kafka配置
 *
 * @author liyi
 * @since 2020-08-26
 */
public class KafkaConst {
    private KafkaConst() {
    }

    /**
     * 生产者配置
     *
     * @return Properties
     */
    public static Properties producerConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_BOOTSTRAP_SERVERS));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_KEY_SERIALIZER));
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_VALUE_SERIALIZER));

        // producer需要server接收到数据之后发出的确认接收的信号 ack 0,1,all
        properties.put(ProducerConfig.ACKS_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_ACKS));

        // 控制生产者发送请求最大大小,默认1M （这个参数和Kafka主机的message.max.bytes 参数有关系）
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_MAX_REQUEST_SIZE));

        // 生产者内存缓冲区大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_BUFFER_MEMORY));

        // 重发消息次数
        properties.put(ProducerConfig.RETRIES_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_RETRIES));

        // 客户端将等待请求的响应的最大时间
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_REQUEST_TIMEOUT_MS));

        // 最大阻塞时间，超过则抛出异常
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_MAX_BLOCK_MS));

        // 通过ssl证书连接kafka配置
        properties.put(ConfigConst.KAFKA_JAAS_CONFIG_CONST,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_JAAS_CONFIG_CONST));
        properties.put(ConfigConst.KAFKA_SASL_MECHANISM_CONST,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_SASL_MECHANISM_CONST));
        properties.put(ConfigConst.KAFKA_SECURITY_PROTOCOL_CONST,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_SECURITY_PROTOCOL_CONST));
        properties.put(ConfigConst.KAFKA_SSL_TRUSTSTORE_LOCATION_CONST,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_SSL_TRUSTSTORE_LOCATION_CONST));
        properties.put(ConfigConst.KAFKA_SSL_TRUSTSTORE_PASSWORD_CONST,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_SSL_TRUSTSTORE_PASSWORD_CONST));
        properties.put(ConfigConst.KAFKA_IDENTIFICATION_ALGORITHM_CONST,
            PluginConfigUtil.getValueByKey(ConfigConst.KAFKA_IDENTIFICATION_ALGORITHM_CONST));
        KafkaConnectBySslSwitch.delKey(properties);
        return properties;
    }
}
