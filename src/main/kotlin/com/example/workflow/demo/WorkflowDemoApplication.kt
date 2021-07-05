package com.example.workflow.demo

import io.camunda.zeebe.client.ZeebeClient
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkflowDemoApplication {
    private val logger = LoggerFactory.getLogger(WorkflowDemoApplication::class.java)

    // Uncomment only when running for the first time, it helps create an instance of a ping job
    init {
        // TODO: This needs to be implemented using DI to prevent issues of double instantiation here and in the controller
//        val client = ZeebeClient.newClientBuilder()
//            .gatewayAddress("127.0.0.1:26500")
//            .usePlaintext()
//            .build()
//
//        val event = client
//            .newCreateInstanceCommand()
//            .bpmnProcessId("demo-workflow")
//            .latestVersion()
//            .variables("{\"status\": \"STARTED\"}")
//            .send()
//            .join()
//
//        logger.info(
//            "WORKFLOW_DEMO: Started instance for workflowKey=${event.processDefinitionKey}" +
//                    ", bpmnProcessId=${event.bpmnProcessId}" +
//                    ", version=${event.version} with" +
//                    " workflowInstanceKey=${event.processInstanceKey}"
//        )
    }
}

fun main(args: Array<String>) {
    runApplication<WorkflowDemoApplication>(*args)
}
