package pl.sda.finance_manager.service;

import com.mysql.cj.util.StringUtils;
import pl.sda.finance_manager.entity.Income;
import pl.sda.finance_manager.repository.IncomeRepository;
import pl.sda.finance_manager.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class IncomeService {

    private final Repository<Income, Long> incomeRepository;

    public IncomeService(Repository<Income, Long> incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void addIncome(double amount, LocalDate date, String commentary) {
        if (amount != 0) {
            Income income = new Income();
            income.setAmount(amount);
            income.setDate(date);
            income.setCommentary(commentary);
            incomeRepository.create(income);
        } else {
            throw new IllegalArgumentException("Provided data is incorrect");
        }
    }

    public void updateIncome(Income income, double amount, LocalDate date, String commentary) {
        if (amount != 0) {
            income.setAmount(amount);
            income.setDate(date);
            income.setCommentary(commentary);
            incomeRepository.update(income);
        } else {
            throw new IllegalArgumentException("Provided data is incorrect");
        }

    }

    public void readAll() {
        List<Income> incomes = incomeRepository.findAll();
        incomes.forEach(each -> System.out.println(each));
    }

    public Income findById(Long id) {
        if (id != null) {
            return incomeRepository.findById(id);
        } else {
            throw new IllegalArgumentException("Provided data is incorrect! ");
        }
    }

    public void deleteById(Long id) {
        if (findById(id) != null) {
            incomeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Provided data is incorrect! ");
        }
    }

    public double sumAllIncomesAmountInTimeRange(String startDate, String endDate) {
        if (!StringUtils.isNullOrEmpty(startDate)) {
            LocalDate startDateParse = LocalDate.parse(startDate);
            LocalDate endDateParse;
            if (StringUtils.isNullOrEmpty(endDate)) {
                endDateParse = LocalDate.now();
            } else {
                endDateParse = LocalDate.parse(endDate);
            }
            double result = ((IncomeRepository) incomeRepository).sumAllIncomesAmountInTimeRange(startDateParse, endDateParse);
            return result;
        } else {
            throw new IllegalArgumentException("Invalid data provided! ");
        }
    }
}
