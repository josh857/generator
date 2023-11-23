package cn.fufeii.learn.test.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * ACCOUNT Repository
 *
 * @author josh
 */

@Repository
public interface AccountPoRepository extends JpaRepository<AccountPo, AccountPoId> {
}