Todos UJUG Demo August 2015
===========================

This is a simple [Dropwizard.io](http://dropwizard.io) project for use in my UJUG talk in August 2015.

## Running

After you've cloned the project switch to that directory and run  

     mvn verify
       
There is a dependency on a postgresql DB running a database called `todos`. The JDBC URL and credentials are configured in the `config.yml` file.

When ready to run grab the `todos.jar` from the `todos-application/target` directory and the `config.yml` from the
`todos-application` directory and run  

    java -jar pathTo/todos.jar db migrate pathTo/config.yml
    java -jar pathTo/todos.jar server pathTo/config.yml
    
And how you should be able to hit the [admin port (http://localhost:8081)](http://localhost:8081) and find the app 
resources running on [http://localhost:8010/todos/](http://localhost:8010/todos/).
    