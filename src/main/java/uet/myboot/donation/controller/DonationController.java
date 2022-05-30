package uet.myboot.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.myboot.donation.models.Donation;
import uet.myboot.donation.service.DonationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    DonationService donationService;

    @GetMapping
    public List<Donation> getAll() {

        return donationService.getAll();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@Valid @RequestBody final Donation donation) {
        return donationService.save(donation) ? "thanh cong" : "that bai";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteById(@Valid @RequestBody final int id) {
        return donationService.deleteBy(id) ? "thanh cong" : "that bai";
    }

}
