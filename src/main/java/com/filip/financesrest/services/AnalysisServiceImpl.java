package com.filip.financesrest.services;

import com.filip.financesrest.models.ILocalDateProjection;
import com.filip.financesrest.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class AnalysisServiceImpl implements AnalysisService
{
	private EntryRepository entryRepository;


	@Autowired
	public AnalysisServiceImpl(EntryRepository entryRepository)
	{
		this.entryRepository = entryRepository;
	}

	@Override
	public List<LocalDate> getDistinctMonthsAndYearsForUser(String username)
	{
		List<ILocalDateProjection> datesList = entryRepository.selectDistinctEntryMonths(username);
		List<LocalDate> list = new ArrayList<>();
		for (ILocalDateProjection dateProj : datesList)
			list.add(LocalDate.of(dateProj.getYear(), dateProj.getMonth(), 1));
		return list;
	}

	@Override
	public int getBalanceForMonthAndCategoryForUser(long categoryId, int month, int year, String username)
	{
		Integer balance = entryRepository.selectBalanceByMonth(categoryId, month, year, username);
		if (balance != null)
			return balance;
		else
			return 0;
	}
}
