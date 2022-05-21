package uet.myboot.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.myboot.donation.models.Donation;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {
}
