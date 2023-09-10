package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);
        return true;
    }

    public synchronized boolean update(Account account) {
        boolean res = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            res = true;
        }
        return res;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean res = false;
        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            int from = getById(fromId).get().amount();
            int to = getById(toId).get().amount();
            if (from >= amount) {
                from -= amount;
                to += amount;
            } else {
                to += from;
                from = 0;
            }
            update(new Account(fromId, from));
            update(new Account(toId, to));
            res = true;
        }
        return res;
    }
}