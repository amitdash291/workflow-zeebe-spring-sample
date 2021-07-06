package com.example.workflow.demo

import com.example.workflow.demo.bootstrap.WorkflowBeanDefinitions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkflowDemoApplication

fun main(args: Array<String>) {
    runApplication<WorkflowDemoApplication>(*args) {
        addInitializers(WorkflowBeanDefinitions.beans())
    }
}
