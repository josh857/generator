package cn.fufeii.learn.test.mp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * TRANSACTION_LOG Repository
 *
 * @author josh
 */

@Repository
public interface TransactionLogPoRepository extends JpaRepository<TransactionLogPo, TransactionLogPoId> {
}