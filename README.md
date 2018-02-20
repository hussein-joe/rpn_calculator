#RPN Calculator
An implementation of RPN calculator. The main idea in this design was to make it easier to
support more operators. 

##### Prerequisites
- JDK 8
- docker (to run docker-based script)
- Maven

##### Run instructions
There are 2 ways to run the application
- Using docker: run the script ``` ./run_using_docker.sh ``` it will build a docker image and then start a container to run the application.
- Using spring boot: run the script ``` ./run_locally.sh ``` it would build the project and use spring-boot to run the application.

##### Code structure and design
I was using TDD to implement all modules. The code is divided to the following parts
- Operator handler: in this part every handler is designed to handle an operator. Operators are mapped to handlers 
dynamically through factory which is configured during the application startup. So, adding new operator would be as easy implementation its handler and 
configure it in the operator handler factory. 

- Calculator: this part is responsible for processing the user input equation, call operators to handle the evaluation, and then collect the result of evaluating the whole equation.

-  Calculator processor: a part which keeps reading from user feeding through to calculator, write the output back to the user.

