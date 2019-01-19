package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        // get the job by id using the findById method, using the instance
        model.addAttribute("job",jobData.findById(id));

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        // If the job form has any errors return user back to the new-job page
        if(errors.hasErrors()){
            model.addAttribute(jobForm); // Send the form back into the view if there is an error
            return "new-job";
        }


        // Create a new job object if there are no errors found and add attributes
        // See job class for constructor

        // builds a  new job object
        Job newJob = new Job();

        // Need to get information from the job form object

        // Getting the fields from the HTML page and adds them to the object
            newJob.setName(jobForm.getName());
            newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
            newJob.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
            newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId()));
            newJob.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypesId()));

        // Adds the new job once all the fields are filled out.
            jobData.add(newJob);

        // redirect to the page of the user once they add it
        return "redirect:?id=" + newJob.getId();

    }
}
