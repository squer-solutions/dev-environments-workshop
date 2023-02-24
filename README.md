# dev-environments-workshop
Workshop for experiencing and comparing various different approaches to creating local development environments.

## What's next?
>__We want to start charging a fee for each trade and store it in our database.__

This task should help to get to know the application. All dependencies should already be defined in the current setup.

>__Additionally, to our crypto assets, we now want to sell stonks as well.__

For this task, we need to get the stock prices from somewhere, as we do not want to make them up. Locally, we can reuse our mock service, to provide price updates for stocks.

>__We heard rumours that our redis cluster is not as reliable as we expected it to be and we might be loosing prices that are only stored on redis.__

We want to introduce a backup storage that guarantees persistence and use redis only as a cache layer on top. Since our postgres DB should not be used to store this information, we need to come up with another solution.

>__For you specific setup, how would you manage to incorporate your integration tests into your automated pipelines?__


>__Think of your past corporate setups - how would you have deployed YOUR Microservice for QA and/or FE Devs?__