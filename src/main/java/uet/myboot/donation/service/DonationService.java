package uet.myboot.donation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.myboot.donation.models.Donation;
import uet.myboot.donation.repository.DonationRepository;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    DonationRepository donationRepository;

    public List<Donation> getAll() {
        return donationRepository.findAll();
    }

    public boolean deleteBy(int id) {
        int before = getAll().size();
        donationRepository.deleteById(id);
        int last = getAll().size();
        return before > last;
    } 

    public boolean save(Donation t) {
        int before = getAll().size();
        donationRepository.save(t);
        int last = getAll().size();
        return before < last;
    }

    public Donation getOne(int id) {
        return donationRepository.findById(id).get();
    }

}
