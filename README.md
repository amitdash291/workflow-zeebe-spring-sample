# Sample Workflow with Zeebe + Spring
This is a sample web service using Zeebe + Spring, with a demo workflow.

### Deploy Demo Workflow on Zeebe Broker
```
docker exec -itu root zeebe ./bin/zbctl --insecure deploy /usr/local/zeebe/workflows/demo-workflow.bpmn
```
