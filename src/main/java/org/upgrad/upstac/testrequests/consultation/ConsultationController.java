package org.upgrad.upstac.testrequests.consultation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.users.User;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;


@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    Logger log = LoggerFactory.getLogger(ConsultationController.class);

    @Autowired
    private TestRequestUpdateService testRequestUpdateService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;

    @Autowired
    TestRequestFlowService  testRequestFlowService;

    @Autowired
    private UserLoggedInService userLoggedInService;


    @GetMapping("/in-queue")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForConsultations()  {

        // Implement this method - <SRINIVASAN PAKKIRISAMY : This method is implemented>

        // Implement this method to get the list of test requests having status as 'LAB_TEST_COMPLETED' - <SRINIVASAN PAKKIRISAMY : RequestStatus class is imported and used>
        // make use of the findBy() method from testRequestQueryService class - <SRINIVASAN PAKKIRISAMY : Completed>
        // return the result - <SRINIVASAN PAKKIRISAMY : Result is returned>
        // For reference check the method getForTests() method from LabRequestController class
        return testRequestQueryService.findBy(RequestStatus.LAB_TEST_COMPLETED);

        // replace this line of code with your implementation - <SRINIVASAN PAKKIRISAMY : This is commented / removed as the method is implemented as expected>
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForDoctor()  {

        //Implement this method - <SRINIVASAN PAKKIRISAMY : This method is implemented>

        // Create an object of User class and store the current logged in user first - <SRINIVASAN PAKKIRISAMY : User object is created. It stores the current logged in user>
        User doctor = userLoggedInService.getLoggedInUser();

        // Implement this method to return the list of test requests assigned to current doctor(make use of the above created User object) - <SRINIVASAN PAKKIRISAMY : User object is used>
        // Make use of the findByDoctor() method from testRequestQueryService class to get the list - <SRINIVASAN PAKKIRISAMY : Implemented>
        // For reference check the method getForTests() method from LabRequestController class
        return testRequestQueryService.findByDoctor(doctor);

        // replace this line of code with your implementation - <SRINIVASAN PAKKIRISAMY : This is commented / removed as the method is implemented as expected>
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");

    }


    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/assign/{id}")
    public TestRequest assignForConsultation(@PathVariable Long id) {

        // Implement this method - <SRINIVASAN PAKKIRISAMY : This method is implemented>

        // Implement this method to assign a particular test request to the current doctor(logged in user)  - <SRINIVASAN PAKKIRISAMY : Implemented>
        // Create an object of User class and get the current logged in user - <SRINIVASAN PAKKIRISAMY : User Object created>
        // Create an object of TestRequest class and use the assignForConsultation() method of testRequestUpdateService to assign the particular id to the current user - <SRINIVASAN PAKKIRISAMY : TestRequest Object created>
        // return the above created object  - <SRINIVASAN PAKKIRISAMY : TestRequest Object returned>
        // For reference check the method assignForLabTest() method from LabRequestController class
        try {
            User doctor = userLoggedInService.getLoggedInUser();
            TestRequest testRequest = testRequestUpdateService.assignForConsultation(id, doctor);
            return testRequest;

            // replace this line of code with your implementation - <SRINIVASAN PAKKIRISAMY : This is commented / removed as the method is implemented as expected>
            // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");

        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/update/{id}")
    public TestRequest updateConsultation(@PathVariable Long id,@RequestBody CreateConsultationRequest testResult) {

        // Implement this method  - <SRINIVASAN PAKKIRISAMY : This method is implemented>

        // Implement this method to update the result of the current test request id with test doctor comments  - <SRINIVASAN PAKKIRISAMY : Implemented>
        // Create an object of the User class to get the logged in user   - <SRINIVASAN PAKKIRISAMY : User Object created>
        // Create an object of TestResult class and make use of updateConsultation() method from testRequestUpdateService class  - <SRINIVASAN PAKKIRISAMY : Implemented>
        // to update the current test request id with the testResult details by the current user(object created)  - <SRINIVASAN PAKKIRISAMY : Implemented>
        // For reference check the method updateLabTest() method from LabRequestController class

        try {
            User doctor = userLoggedInService.getLoggedInUser();
            TestRequest testRequest = testRequestUpdateService.updateConsultation(id,testResult, doctor);
            return testRequest;

            // replace this line of code with your implementation - <SRINIVASAN PAKKIRISAMY : This is commented / removed as the method is implemented as expected>
            // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");

        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }

}
