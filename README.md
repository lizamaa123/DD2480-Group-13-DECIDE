# KTH Course: DD2480 - Group 13 | LAB 1: Decide

This repository contains the solution for ```DECIDE()``` function for the (version 5) "Launch Interceptor Program: Requirements Specification" (Knight, Leveson, 2016), as the first lab-assignment in the course DD2480, at KTH. 

The program is a "hypothetical anti-ballistic missile system" where the assignment is to write the ```DECIDE()``` function. The function will determine if an interceptor should be launched, based on various combinations of so-called Launch Interceptor Conditions (LIC's). Depending on the combination of LIC's, the function will return a final output as a boolean signal; ```"YES"``` or ```"NO"```.

## Prerequisites

To build and run this project, you will need:

* **Java:** Version 8
* **Maven:** Version 3.9.12

**OBS!** Newer version may also work, but this project has been specifically built and tested with the mentioned versions.

### Executing program

1. **Clone the repository**

2. **Run Unit Tests**
    
    You can execute all the tests in ```src/test/java/lab1``` by entering following terminal command: 
    ```
    mvn compile
    mvn test
    ```

3. **Build and Run application**

To run the main program, enter the following terminal command: 
```
mvn compile               
mvn exec:java -Dexec.mainClass="lab1.Main"
```

### Project Structure
- ```src/main/java/lab1/Decide.java```: The main application containing the ```DECIDE()``` function and all LIC logic (incl. helper methods).
-  ```src/test/java/lab1/```: Contains all unit tests.
    - ```LicTest.java```, ```CMVTest.java``` and etc.: unit tests for specific modules.
    -  ```Main.java```: Tests the entire ```DECIDE()``` flow (Inputs -> Launch/No Launch).


## Statement of contributions

### Member Contributions

**Elinor Selinder:**
- Wrote solutions and unit tests for LIC's 13 and 14
- Wrote solution for CMV implementation and corresponding unit tests
- Reviewed and merged PR's for following issues: LIC 3, 11, 8 (with its corresponding unit tests) and README.md

**Omar Almassri:**
- Wrote solutions and unit tests for LIC's 10, 11, 12
- Wrote solution for PUM and corresponding unit tests
- Reviewed and merged PR's for following issues: LIC 2, 5, 7, ```DECIDE()``` (with its corresponding unit tests) and README.md

**Hannes Westerberg:**
- Wrote solutions and unit tests for LIC's 4, 5, 6
- Wrote solution for FUV implementation and corresponding unit tests
- Reviewed and merged PR's for following issues: LIC 0, 10, 14, CMV (with its corresponding unit tests) and README.md

**Helin Saeid:**
- Wrote solutions and unit tests for LIC's 7, 8, 9 
- Wrote solution for ``DECIDE()`` 
- Reviewed and merged PR's for following issues: LIC 1, 4, 12 and PUM (with its corresponding unit tests)

**Liza Aziz:**
- Wrote solutions and unit tests for LIC's 0, 1, 2, 3
- Wrote program in ```Main.java``` and unit test for `DECIDE()`
- Created and organized project folders/files/code, incl. setting up Maven implementation and handling documentation (README.md & LICENSE.md). 
- Reviewed and merged PR's for following issues: LIC 6, 9, 13 and and FUV (with its corresponding unit tests)

### Remarkable
We believe that our own contribution is something of a remarkable sort, especially in the way we have worked with quality assurance and or worklflow. Each member have had some previous experience working with one project in a larger group, but this was put to the test for this assignment. We had to learn very quickly to understand the established workflow and to continously communicate in case of any obstacles. Even with some previous knowledge on how to work with, e.g. Github, we have proved amazing result on the way we have collaborated especially when assigning issues to each other and handling merge conflicts. We have followed a strict workflow:
1. Creating an issue and assign yourself (or a member)
2. Creating a new branch for that issue
3. Create a pull request and assign chosen member (peer review)
4. Reviewer merges branch (or resolves merge if its easy, otherwise author will have to redo work).
 
We believe that we have since start tried to utilize and work the same way professionals do it, and believe we have achieved that which makes us very proud of our work. This can also be seen in the way we have titled our issues as well as all the pull requests (synchronized). 

Besides that, we quickly established the logistics in how we would divide the work to make sure that everyone would have some part in other sub-assignments. This was done by ensuring that the same member who wrote the code would not review it, but instead assign it to someone else. In this way, any bugs, repetition of code, confusing code logistic etc. would be quickly noticed and handled early on. We also utilized Github Projects which have been very helpful in organizing the work, and have all strictly followed the tickets on the board to minimize double-work and miscommunication. 

Most importantly, we are very proud of the way we have managed to work and collaborate under such short time constrain, and will continue to improve our communication and workflow to work as effectively as possible and create great results. 

### Essence Standard
We have evaluated the checklist on page 60 of the Essence Standard and determined we are in state "Foundation Established". We have succesfully agreed on the chosen foundational tools (Java for programming, Maven for building and testing, Discord for communication, Github for version control). We have also agreed on our Code of Conduct and our assigned workload. Since this is our first project together, we have identified the gaps between our plan and our execution. For example, our plan was to dictate strict internal deadlines. However, in reality, we discovered a gap in our time estimation capabilities which lead to delays that squeezed our schedule. We are not yet in the next state "In use" because we have yet to discuss feedbacks relating to our way of working. For example, we have not yet established a consistent schedule for meetings and we have struggled with setting and adhering to these strict internal deadlines. The obstacles to reach "In use" are ensuring to overcome these time management challenges and that everybody is consistently following the chosen protocols for writing, publishing and reviewing code. Overcoming these obstacles will increase the communication and synchronize us more as a team. 

## License
This project is licensed under the terms of the MIT license.
