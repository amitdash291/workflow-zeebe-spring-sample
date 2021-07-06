package com.example.workflow.demo.bootstrap

import io.camunda.zeebe.client.ZeebeClient
import org.springframework.context.support.beans

object WorkflowBeanDefinitions {
    fun beans() = beans {
        bean<ZeebeClient> {
            ZeebeClient.newClientBuilder()
                .gatewayAddress("127.0.0.1:26500")
                .usePlaintext()
                .build()
        }
    }
}