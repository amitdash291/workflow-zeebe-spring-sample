package com.example.workflow.demo.controllers

import io.camunda.zeebe.client.ZeebeClient
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("pings")
class PingController {
    private val logger = LoggerFactory.getLogger(PingController::class.java)

    @GetMapping("/{message}")
    fun getPing(@PathVariable message: String): String {
        completePingWorker()
        return "pong"
    }

    private fun completePingWorker() {
        val client = ZeebeClient.newClientBuilder()
            .gatewayAddress("127.0.0.1:26500")
            .usePlaintext()
            .build()
        val worker = client.newWorker()
            .jobType("ping-job")
            .handler { jobClient, job ->
                logger.info("WORKFLOW_DEMO: Ping Job workflow step is completed, job-key: ${job.key}")
                jobClient.newCompleteCommand(job.key)
                    .variables("{\"status\": \"COMPLETED\"}")
                    .send()
                    .join()
            }
            .open()
        Thread.sleep(1000)
        worker.close()
        logger.info("WORKFLOW_DEMO: Ping Job worker is completed")
    }
}