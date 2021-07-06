package com.example.workflow.demo.controllers

import io.camunda.zeebe.client.ZeebeClient
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("pings")
class PingController(private val client: ZeebeClient) {
    private val logger = LoggerFactory.getLogger(PingController::class.java)

    @PutMapping("/{message}")
    fun processPing(@PathVariable message: String): String {
        completePingWorker(message)
        return "pong"
    }

    @PostMapping
    fun addPing() {
        val event = client
            .newCreateInstanceCommand()
            .bpmnProcessId("demo-workflow")
            .latestVersion()
            .variables("{\"status\": \"STARTED\"}")
            .send()
            .join()

        logger.info(
            "WORKFLOW_DEMO: Started instance for workflowKey=${event.processDefinitionKey}" +
                    ", bpmnProcessId=${event.bpmnProcessId}" +
                    ", version=${event.version} with" +
                    " workflowInstanceKey=${event.processInstanceKey}"
        )
    }

    private fun completePingWorker(message: String) {
        val worker = client.newWorker()
            .jobType("process-ping")
            .handler { jobClient, job ->
                logger.info("WORKFLOW_DEMO: Ping Job workflow step is completed, job-key: ${job.key}")
                jobClient.newCompleteCommand(job.key)
                    .variables("{\"status\": \"PROCESSED\", \"message\": \"$message\"}")
                    .send()
                    .join()
            }
            .open()
        Thread.sleep(1000)
        worker.close()
        logger.info("WORKFLOW_DEMO: Ping Job worker is completed")
    }
}