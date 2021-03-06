package com.filip.financesrest.repositories;


import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.ILocalDateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<FinanceEntry, Long>
{
	List<FinanceEntry> findByUser_UsernameOrderByDateAsc(String username);

	List<FinanceEntry> findByUser_UsernameOrderByDateDesc(String username);

	List<FinanceEntry> findByUser_UsernameOrderByValueAsc(String username);

	List<FinanceEntry> findByUser_UsernameOrderByValueDesc(String username);

	List<FinanceEntry> findByUser_UsernameAndCategory_Id(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByDateAsc(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByDateDesc(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByValueAsc(String username, Long id);

	List<FinanceEntry> findByUser_UsernameAndCategory_IdOrderByValueDesc(String username, Long id);

	@Query(value = " SELECT DISTINCT MONTH(finance_entry.date) AS month, YEAR(finance_entry.date) AS year " +
			"FROM finance_entry JOIN user " +
			"ON user.id = finance_entry.user_id " +
			"WHERE user.username = ?1 ORDER BY date DESC", nativeQuery = true)
	List<ILocalDateProjection> selectDistinctEntryMonths(String username);

	@Query(value = "SELECT SUM(value) " +
			"FROM finance_entry JOIN user " +
			"ON user.id = finance_entry.user_id " +
			"WHERE category_id = ?1 " +
			"AND MONTH(date) = ?2 " +
			"AND YEAR(date) = ?3 " +
			"AND user.username = ?4 ", nativeQuery = true)
	Integer selectBalanceByCategoryAndMonth(long categoryId, int month, int year, String username);


	@Query(value =  "select distinct year(finance_entry.date) as year " +
			"from finance_entry join user on user.id = finance_entry.user_id" +
			" where user.username = ?1", nativeQuery = true)
	List<Integer> selectDistinctYearsForUser(String username);


	@Query(value="select sum(finance_entry.value) as balance" +
			" from finance_entry join user on user.id = finance_entry.user_id" +
			" where month(date) = ?1 and year(date) = ?2 and username = ?3",nativeQuery = true)
	Double selectBalanceForMonthAndYearForUser(int month, int year, String username);



}
