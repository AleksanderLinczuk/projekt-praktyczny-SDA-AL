package pl.sda.finance_manager.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import pl.sda.finance_manager.DbConnection;
import pl.sda.finance_manager.entity.Category;
import pl.sda.finance_manager.entity.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ExpenseRepository implements Repository<Expense, Long> {


    @Override
    public void create(Expense object) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Expense> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Expense> incomeList = entityManager.createQuery("FROM Expense", Expense.class).getResultList();
        entityManager.close();
        return incomeList;
    }

    @Override
    public Expense findById(Long id) {
        EntityManager entityManager = DbConnection.getEntityManager();
        Expense expense = entityManager.find(Expense.class, id);
        entityManager.close();
        return expense;
    }

    @Override
    public void update(Expense object) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Expense object) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Optional<Expense> expense = Optional.ofNullable(entityManager.find(Expense.class, id));
        expense.ifPresent(e -> entityManager.remove(e));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Expense> findExpensesFilteredByCategory(Category selectedCategory) {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Expense> query = entityManager.createQuery("FROM Expense WHERE category.id = :selectedCategory", Expense.class);
        List<Expense> expenses = query.setParameter("selectedCategory", selectedCategory.getId()).getResultList();
        entityManager.close();
        return expenses;
    }

    public double sumAllExpensesAmount() {
        EntityManager entityManager = DbConnection.getEntityManager();
        double result = 0;
        try {
            result += entityManager.createQuery("SELECT SUM(amount) FROM Expense", double.class).getSingleResult();
        } catch (NullPointerException ignored) {
        }
        entityManager.close();
        return result;
    }

    public double sumAllExpensesAmountInTimeRange(LocalDate startDate, LocalDate endDate) {
        double result = 0;
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Double> query = entityManager.createQuery("SELECT SUM(amount) FROM Expense WHERE date BETWEEN :startDate AND :endDate", double.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        try{
            result += query.getSingleResult();
        }catch (NullPointerException ignored){
        }
        entityManager.close();
        return result;
    }

    public List<Object[]> findSumOfExpensesGroupedByCategory() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Object[]> result = entityManager.createQuery("SELECT SUM(amount),COUNT(*), category.name FROM Expense GROUP BY category.name", Object[].class).getResultList();
        entityManager.close();
        return result;
    }

}
