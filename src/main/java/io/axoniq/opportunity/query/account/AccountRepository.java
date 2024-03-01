package io.axoniq.opportunity.query.account;

import org.springframework.data.jpa.repository.JpaRepository;

interface AccountRepository extends JpaRepository<AccountView, String> {

}
