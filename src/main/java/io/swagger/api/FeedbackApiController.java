package io.swagger.api;

import io.swagger.model.ErrorResponse;
import io.swagger.model.Feedback;
import io.swagger.model.FeedbackInput;
import io.swagger.model.SuccessfulResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import com.cloudant.client.api.Database;
import org.springframework.beans.factory.annotation.Autowired;
import com.cloudant.client.api.model.Response;
import java.util.ArrayList;
import java.util.Date;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-31T21:52:44.949Z")

@Controller
public class FeedbackApiController implements FeedbackApi {

    private static final Logger log = LoggerFactory.getLogger(FeedbackApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
private Database feedbackDB;


    @org.springframework.beans.factory.annotation.Autowired
    public FeedbackApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Feedback>> getFeedback() {
        List<Feedback> allDocs = new ArrayList<Feedback>();
        try {
            allDocs = feedbackDB.getAllDocsRequestBuilder()
                                .includeDocs(true)
                                .build()
                                .getResponse()
                                .getDocsAs(Feedback.class);
        } catch (Exception e) {
            log.error("error occurred: {}", e.getLocalizedMessage());
            return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Feedback>>(allDocs, HttpStatus.OK);
    }

    public ResponseEntity<SuccessfulResponse>
        createFeedback(@ApiParam(value = ""  )  @Valid @RequestBody
        FeedbackInput body) {
            Response response;
            SuccessfulResponse successfulResponse;
            Feedback newFeedback = new Feedback();
            Date d = new Date();
            newFeedback.setCreatedTs(d.toString());
            newFeedback.setFeedback(body.getFeedback());
            newFeedback.setName(body.getName());
            newFeedback.setRating(body.getRating());
            try {
                response = feedbackDB.save(newFeedback);
                successfulResponse = new SuccessfulResponse();
                successfulResponse.setId(response.getId());
                successfulResponse.setRev(response.getRev());
            } catch (Exception e) {
                log.error("error occurred: {}", e.getLocalizedMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            ResponseEntity<SuccessfulResponse> responseEntity = new ResponseEntity<SuccessfulResponse>(successfulResponse, HttpStatus.OK);
            return responseEntity;
        }

    

}
