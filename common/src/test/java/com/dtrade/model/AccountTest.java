package com.dtrade.model;

import com.dtrade.model.account.Account;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void constructor_setsMailAndPassword() {
        Account account = new Account("test@example.com", "password123");

        assertEquals("test@example.com", account.getMail());
        assertEquals("password123", account.getPassword());
    }

    @Test
    public void defaultConstructor_createsEmptyAccount() {
        Account account = new Account();

        assertNull(account.getMail());
        assertNull(account.getPassword());
    }

    @Test
    public void getUsername_returnsMail() {
        Account account = new Account("user@test.com", "pwd");

        assertEquals("user@test.com", account.getUsername());
    }

    @Test
    public void isAdmin_withAdminRole_returnsTrue() {
        Account account = new Account();
        account.setRole(Account.F_ROLE_ADMIN);

        assertTrue(account.isAdmin());
    }

    @Test
    public void isAdmin_withAccountRole_returnsFalse() {
        Account account = new Account();
        account.setRole(Account.F_ROLE_ACCOUNT);

        assertFalse(account.isAdmin());
    }

    @Test
    public void getAuthorities_returnsCorrectRole() {
        Account account = new Account();
        account.setRole(Account.F_ROLE_ACCOUNT);

        Collection<GrantedAuthority> authorities = account.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals(Account.F_ROLE_ACCOUNT,
                authorities.iterator().next().getAuthority());
    }

    @Test
    public void isAccountNonExpired_returnsTrue() {
        Account account = new Account();
        assertTrue(account.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked_returnsTrue() {
        Account account = new Account();
        assertTrue(account.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired_returnsTrue() {
        Account account = new Account();
        assertTrue(account.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled_defaultFalse() {
        Account account = new Account();
        assertFalse(account.isEnabled());
    }

    @Test
    public void isEnabled_whenSet_returnsTrue() {
        Account account = new Account();
        account.setEnabled(true);
        assertTrue(account.isEnabled());
    }

    @Test
    public void toString_containsId() {
        Account account = new Account();
        account.setId(42L);

        String str = account.toString();
        assertTrue(str.contains("42"));
    }
}
